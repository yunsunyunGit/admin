package com.yunsunyun.point.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunsunyun.config.util.ConfigUtil;
import com.yunsunyun.point.mapper.PtGoodsMapper;
import com.yunsunyun.point.po.PtMallMerchant;
import com.yunsunyun.point.service.IPtMallMerchantService;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.dao.system.KnRoleDao;
import com.yunsunyun.xsimple.entity.IdEntity;
import com.yunsunyun.xsimple.util.dete.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yunsunyun.xsimple.ShiroUser;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.yunsunyun.xsimple.api.system.EmpOrg;
import com.yunsunyun.xsimple.api.system.EmpPos;
import com.yunsunyun.xsimple.attachment.exception.AttachmentIsNullException;
import com.yunsunyun.xsimple.entity.system.KnEmployee;
import com.yunsunyun.xsimple.entity.system.KnPositionBranchedPassage;
import com.yunsunyun.xsimple.entity.system.KnUser;
import com.yunsunyun.xsimple.mxm.util.DateUtils;
import com.yunsunyun.xsimple.mxm.util.JsonUtils;
import com.yunsunyun.xsimple.service.system.OrganizationService;
import com.yunsunyun.xsimple.service.system.ResourceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import com.kingnode.diva.mapper.JsonMapper;
import com.kingnode.diva.security.utils.Digests;
import com.kingnode.diva.utils.Encodes;
import com.kingnode.diva.web.Servlets;

/**
 * 经销商管理
 *
 * @author chirs@zhoujin.com (Chirs Chou)
 */
@Controller
@RequestMapping(value = "/point/merchant")
public class PtMerchantController {
    @Autowired
    private OrganizationService os;
    @Autowired
    private ResourceService rs;
    @Autowired
    private KnRoleDao roleDao;
    @Autowired
    private PtGoodsMapper goodsMapper;
    @Autowired
    private IPtMallMerchantService iPtMallMerchantService;

    private static Logger logger = LoggerFactory.getLogger(PtMerchantController.class);

    /**
     * 打开经销商管理页面
     *
     * @return
     */
    @RequiresPermissions("point-merchant")
    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "point/merchantList";
    }

    /**
     * 对员工分页显示
     *
     * @param dt
     * @param request
     *
     * @return
     */
    @RequiresPermissions("point-merchant")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public DataTable<KnEmployee> list(DataTable<KnEmployee> dt, ServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        return os.PageKnMerchant(searchParams, dt);
    }

    /**
     * 打开创建员工页面
     *
     * @return
     */
    @RequiresPermissions("point-merchant")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("action", "create");
        KnUser u = new KnUser();
        KnEmployee e = new KnEmployee();
        e.setEmail("电子邮件地址");
        u.setId(0L);
        u.setSalt(Encodes.encodeHex(Digests.generateSalt(ResourceService.SALT_SIZE)));
        model.addAttribute("u", u);
        model.addAttribute("e", e);
        model.addAttribute("orgTree", JsonUtils.toJson(os.OrganizationJsTree()));
        model.addAttribute("posTree", JsonUtils.toJson(os.PositionJsTree()));
        model.addAttribute("roles", rs.ListKnRoleExitsActive());
        model.addAttribute("teams", os.ListTeam());
        return "point/merchantForm";
    }

    /**
     * 保存用户
     *
     * @param user
     *
     * @return
     */
    @RequiresPermissions("point-merchant")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid KnUser user, @Valid KnEmployee emp, @RequestParam(value = "roles", defaultValue = "") Long[] roles,
//                         @RequestParam(value = "teams", defaultValue = "") Long[] teams, @RequestParam(value = "empOrg", defaultValue = "") String org,
//                         @RequestParam(value = "empPos", defaultValue = "") String pos,
                         @RequestParam("files") MultipartFile file, RedirectAttributes redirectAttributes
//                         @RequestParam(value = "workT", defaultValue = "") String workT, @RequestParam(value = "turnoverT", defaultValue = "") String turnoverT
    ) {
//        JsonMapper jm = JsonMapper.nonDefaultMapper();
        // emp.setEmail(emp.getLoginName()+"@kingnode.com");
        roles = new Long[]{roleDao.findRoleByCode("merchant").getId()};
        List<EmpOrg> empOrg = new ArrayList<>();
        List<EmpPos> empPos = new ArrayList<>();
        Long workTime = System.currentTimeMillis();
        Long turnoverTime = System.currentTimeMillis();
        emp.setWorkingTime(workTime);
        emp.setTurnoverTime(turnoverTime);
        emp.setUserType("merchant");
        if("ENABLE".equals(user.getStatus())){
            emp.setJob(IdEntity.ActiveType.valueOf("ENABLE"));
        }else{
            emp.setJob(IdEntity.ActiveType.valueOf("DISABLE"));
        }
        os.SaveKnEmployee(user, emp, roles, new Long[0], empOrg, empPos);
        if (!file.isEmpty()) {
            // String name = file.getOriginalFilename();
            // int i = file.getOriginalFilename().lastIndexOf(".");
            // emp.setImageAddress(Setting.USERIMAGE + user.getId() + (i >= 0 ?
            // name.substring(i) : ".jpg"));
            // updateImg(emp, file);
            logger.debug("emp -- id :" + emp.getId());
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            // AttachmentUtil.saveFile(file, loginUser.getId(),
            // "KnEmployee", emp.getId(), "");
            String name = file.getOriginalFilename();
            int i = file.getOriginalFilename().lastIndexOf(".");
            String imageUrl = Setting.USERIMAGE + "/" + user.getId() + (i >= 0 ? name.substring(i) : ".jpg");
            emp.setImageAddress(ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name()) + imageUrl);
            String path = ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name()) + imageUrl;
            File destFile = updateImg(path, file);
            os.SaveKnEmployee(emp);
        }
        redirectAttributes.addFlashAttribute("stat", true);
        redirectAttributes.addFlashAttribute("message", "新建商家成功!");
        return "redirect:/point/merchant";
    }

    /**
     * 跳转更新员工
     *
     * @param id
     *
     * @return
     */
    @RequiresPermissions("point-merchant")
    @RequestMapping(value = "update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("u", rs.ReadUser(id));
        KnEmployee ke = os.ReadKnEmployee(id);
        ke.setImageAddress(Strings.isNullOrEmpty(ke.getImageAddress()) ? "" : ke.getImageAddress());
        model.addAttribute("e", ke);
        model.addAttribute("orgTree", JsonUtils.toJson(os.OrganizationJsTree()));
        model.addAttribute("posTree", JsonUtils.toJson(os.PositionJsTree()));
        model.addAttribute("roles", rs.ListKnRoleExitsActive());
        model.addAttribute("teams", os.ListTeam());
        // 获取上级人员信息
        KnPositionBranchedPassage branchedPassage = os.ReadKnPositionBranchedPassage(id);
        model.addAttribute("leaderId", branchedPassage == null ? "" : branchedPassage.getLeaderId());
        model.addAttribute("leaderName", branchedPassage == null ? "" : branchedPassage.getLeaderName());
        return "/point/merchantForm";
    }

    /**
     * 更新员工
     *
     * @param user
     *
     * @return
     * @throws IOException
     * @throws AttachmentIsNullException
     */
    @RequiresPermissions("point-merchant")
    @RequestMapping(value = "update")
    public String update(@Valid @ModelAttribute("user") KnUser user, @Valid @ModelAttribute("emp") KnEmployee emp,
                         @RequestParam(value = "roles", defaultValue = "") Long[] roles, @RequestParam(value = "teams", defaultValue = "") Long[] teams,
                         @RequestParam(value = "empOrg", defaultValue = "") String org, @RequestParam(value = "empPos", defaultValue = "") String pos,
                         @RequestParam("files") MultipartFile file, RedirectAttributes redirectAttributes, @RequestParam(value = "workT", defaultValue = "") String workT,
                         @RequestParam(value = "turnoverT", defaultValue = "") String turnoverT) throws AttachmentIsNullException, IOException {
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();
            int i = file.getOriginalFilename().lastIndexOf(".");
            String imageUrl = Setting.USERIMAGE + "/" + DateUtil.getNowYear() + DateUtil.getMonth() + "/" + DateUtil.getDay() + "/" + user.getId() + (i >= 0 ? name.substring(i) : ".jpg");
            emp.setImageAddress(ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name()) + imageUrl);
            String path = ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name()) + imageUrl;
            File destFile = updateImg(path, file);


            // by baddyzhou  on 20170922
//			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
//			try {
//				AttachmentUtil.removeAttachment("KnEmployee", emp.getId().toString());
//				System.out.println("file.getOriginalFilename()" + file.getOriginalFilename());
//				ImgCompress imgCompress = new ImgCompress(file.getInputStream());
//				InputStream inputStream = imgCompress.resize(150, 150);
//				AttachmentUtil.saveToMongoDB(loginUser.id, "KnEmployee", emp.getId().toString(), "电脑端图片更新", emp.getId() + ".jpg", inputStream);
//				emp.setImageAddress("/uf/employee/photo/" + emp.getId());
//			} catch (AttachmentTypeNotAllowedException e) {
//				redirectAttributes.addFlashAttribute("stat", false);
//				redirectAttributes.addFlashAttribute("message", e.getMessage());
//				return "redirect:/system/employee";
//			}
        }
        JsonMapper jm = JsonMapper.nonDefaultMapper();
        List<EmpOrg> empOrg = jm.fromJson(org, new TypeReference<List<EmpOrg>>() {
        });

        List<EmpPos> empPos = jm.fromJson(pos, new TypeReference<List<EmpPos>>() {
        });
        Long workTime = DateUtils.dateToTime(workT);
        Long turnoverTime = DateUtils.dateToTime(turnoverT);
        emp.setWorkingTime(workTime);
        emp.setTurnoverTime(turnoverTime);
        if("ENABLE".equals(user.getStatus())){
            emp.setJob(IdEntity.ActiveType.valueOf("ENABLE"));
        }else{
            emp.setJob(IdEntity.ActiveType.valueOf("DISABLE"));
        }
        os.SaveKnEmployee(user, emp, roles, teams, empOrg, empPos);

        //消息通道暂时关闭，不用调用消息通知 by baddyzhou on 20170922
        //List<Long> userIds = employeeDao.queryEmployee();
        //new CommonSaveData().saveUpdatedImgData(emp.getId().toString(), userIds);

        redirectAttributes.addFlashAttribute("stat", true);
        redirectAttributes.addFlashAttribute("message", "更新商家成功");
        return "redirect:/point/merchant";
    }

    /**
     * 检查用户名是否存在
     *
     * @param loginName
     *
     * @return
     */
    @RequestMapping(value = "check/{id}")
    @ResponseBody
    public Boolean check(@PathVariable("id") Long id, @RequestParam("loginName") String loginName) {
        return rs.checkLoginName(loginName, id);
    }

    /**
     * 删除用户
     *
     * @param id
     * @param redirectAttributes
     *
     * @return
     */
    @RequiresPermissions("point-merchant")
    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public Map delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Map map = os.DeleteKnEmployee(id);
        //删除此商家入驻的商城
        boolean flag = (boolean)map.get("stat");
        if (flag){
           iPtMallMerchantService.delete(new EntityWrapper<PtMallMerchant>().eq("merchant_id",id));
           //删除商家时，软删除该商家的所有商品
           goodsMapper.deleteByMerchantId(id);
        }
        return map;
    }

    private File updateImg(String path, MultipartFile file) {
        try {
            File localFile = new File(path);
            logger.debug("文件保存的完整路径为：" + localFile.getAbsolutePath());
            if (!localFile.getParentFile().exists()) {
                localFile.getParentFile().mkdirs();
            }
            file.transferTo(localFile);
            return localFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ModelAttribute
    public void ReadKnRole(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("user", rs.ReadUser(id));
            model.addAttribute("emp", os.ReadKnEmployee(id));
        }
    }



}