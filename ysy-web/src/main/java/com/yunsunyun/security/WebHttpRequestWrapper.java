package com.yunsunyun.security;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.MultiValueMap;

public class WebHttpRequestWrapper extends HttpServletRequestWrapper {

	private Logger logger = LoggerFactory.getLogger(WebHttpRequestWrapper.class);
	
	private byte[] data;
	
	private WebSecurityConfig config = new WebSecurityConfig();

	private final FormHttpMessageConverter formConverter = new AllEncompassingFormHttpMessageConverter();

	/**
	 * 封装http请求
	 * 
	 * @param request
	 */
	public WebHttpRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (data == null) {
			ServletInputStream inputStream = super.getInputStream();
			data = IOUtils.toByteArray(inputStream);
		}
		ServletInputStream byteArrayInputStream = new ByteArrayServletInputStream(data); 
		return byteArrayInputStream;
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		// 若开启特殊字符替换，对特殊字符进行替换
		if (config.isReplace()) {
			String regex = config.getRegex();
			WebSecurityManager.securityReplace(name, regex);
		}
		return value;
	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		// 若开启特殊字符替换，对特殊字符进行替换
		if (config.isReplace()) {
			String regex = config.getRegex();
			WebSecurityManager.securityReplace(name, regex);
		}
		return value;
	}

	/**
	 * 没有违规的数据，就返回false;
	 * 
	 * @return
	 */
	private boolean checkHeader() {
		String regex = this.config.getRegex();
		Enumeration<String> headerParams = this.getHeaderNames();
		while (headerParams.hasMoreElements()) {
			String headerName = headerParams.nextElement();
			String headerValue = this.getHeader(headerName);
			if (WebSecurityManager.matches(headerValue, regex)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 没有违规的数据，就返回false;
	 * 
	 * @return
	 */
	private boolean checkParameter() {
		String regex = this.config.getRegex();
		Map<String, String[]> params = new HashMap<String, String[]>(getParameterMap());
		String method = this.getMethod();
		String encoding = getCharacterEncoding();
		if (params.isEmpty() && "post".equalsIgnoreCase(method)) {
			// Check that we have a file upload request
			boolean isMultipart = ServletFileUpload.isMultipartContent(this);
			if (isMultipart) {
				try {
					// Create a factory for disk-based file items
					DiskFileItemFactory factory = new DiskFileItemFactory();

					int maxMemorySize = 1024 * 1000 * 2;
					// Set factory constraints
					factory.setSizeThreshold(maxMemorySize);
					File tempDirectory = File.createTempFile("temp_", ".tmp").getParentFile();
					factory.setRepository(tempDirectory);

					// Create a new file upload handler
					ServletFileUpload upload = new ServletFileUpload(factory);

					long maxRequestSize = 1024 * 1000 * 100;
					// Set overall request size constraint
					upload.setSizeMax(maxRequestSize);

					// Parse the request
					List<FileItem> fileItems = upload.parseRequest(this);
					for (FileItem item : fileItems) {
						if (item.isFormField()) {
						    String k = item.getFieldName();
						    String v = null;
						    if (encoding == null) {
						    	v = item.getString();
						    } else {
						    	v = item.getString(encoding);
						    }
						    params.put(k, new String[]{v});
						}
					}
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				} catch (FileUploadException e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				// 其他类型的POST提交
				try {
					final HttpServletRequest _request = this;
					HttpInputMessage inputMessage = new ServletServerHttpRequest(_request) {
						@Override
						public InputStream getBody() throws IOException {
							return _request.getInputStream();
						}

						@Override
						public HttpHeaders getHeaders() {
							HttpHeaders httpHeaders = super.getHeaders();
							if (httpHeaders != null && httpHeaders.getContentType() == null) {
								httpHeaders.setContentType(MediaType.APPLICATION_JSON);
							}
							return httpHeaders;
						}
					};
					MultiValueMap<String, String> formParameters = formConverter.read(null, inputMessage);

					Set<String> keys = formParameters.keySet();
					for (String k : keys) {
						List<String> valueList = formParameters.get(k);
					    String[] values = new String[valueList.size()];
						params.put(k, valueList.toArray(values));
					}
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		Set<String> names = params.keySet();
		for (String k : names) {
			String[] values = params.get(k);
			for (String v : values) {
				if (WebSecurityManager.matches(v, regex)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 没有违规的数据，就返回false; 若存在违规数据，根据配置信息判断是否跳转到错误页面
	 * 
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public boolean validateParameter() throws ServletException, IOException {
		// 开始header校验，对header信息进行校验
		if (config.isCheckHeader()) {
			if (this.checkHeader()) {
				return true;
			}
		}
		// 开始parameter校验，对parameter信息进行校验
		if (config.isCheckParameter()) {
			if (this.checkParameter()) {
				return true;
			}
		}
		return false;
	}

	public void setXssSecurityConfig(WebSecurityConfig xssSecurityConfig) {
		this.config = xssSecurityConfig;
	}

}