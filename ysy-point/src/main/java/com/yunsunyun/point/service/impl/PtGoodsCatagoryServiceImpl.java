package com.yunsunyun.point.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunsunyun.config.util.ConfigUtil;
import com.yunsunyun.point.mapper.PtMallCatagoryMapper;
import com.yunsunyun.point.po.PtGoodsCatagory;
import com.yunsunyun.point.mapper.PtGoodsCatagoryMapper;
import com.yunsunyun.point.po.PtMallCatagory;
import com.yunsunyun.point.service.IPtGoodsCatagoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.api.common.JsTree;
import com.yunsunyun.xsimple.mxm.util.ImgCompress;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@Transactional
@Service
public class PtGoodsCatagoryServiceImpl extends ServiceImpl<PtGoodsCatagoryMapper, PtGoodsCatagory> implements IPtGoodsCatagoryService {

    @Autowired
    private PtGoodsCatagoryMapper catagoryMapper;
    @Autowired
    private PtMallCatagoryMapper mallCatagoryMapper;

    /**
     * 获取分类列表树状结构
     *
     * @return
     */
    @Override
    public List<JsTree> getCatagoryTree() {
        List<PtGoodsCatagory> list = catagoryMapper.selectList(new EntityWrapper<PtGoodsCatagory>().where("is_deleted != 1").orderBy("seq"));
        Map<String, JsTree> resMap = Maps.newHashMap();// 数据库中的节点列表
        List<JsTree> tree = Lists.newArrayList();
        for (PtGoodsCatagory cata : list) {
            JsTree node = new JsTree();
            String id = cata.getId().toString();
            resMap.put(id, node);
            node.setId(id);
            Long supId = cata.getParentId();
            boolean isRootNode = (supId == null || supId.compareTo(0L) <= 0);// 一级节点的判断条件
            node.setParent(isRootNode ? "#" : supId.toString());
            node.setText(cata.getName());
            if(cata.getParentId() == 0l) {
                node.setIcon("fa fa-home");
            }else{
                node.setIcon("fa fa-tint");
            }
            if (isRootNode) {
                // 只挂载一级节点，其他节点都挂载到这些节点下
                tree.add(node);
            }
        }
        // 如果节点对应的父节点不存在，则会无法加载整颗js树
        for (Map.Entry<String, JsTree> entry : resMap.entrySet()) {
            JsTree node = entry.getValue();
            String id = node.getId();
            String parent = node.getParent();
            boolean isRootNode = ("#".equals(parent));// 一级节点的判断条件
            if (!isRootNode) {
                JsTree parentNode = resMap.get(parent);
                if (parentNode == null) {
                    System.out.printf("数据错误，节点[%s]的父节点[%s]不存在!\n", id, parent);
                    continue;
                } else {
                    tree.add(node);
                }
            }
        }
        return tree;
    }

    @Override
    public Long insertCatagoryReturnId(PtGoodsCatagory catagory) {
        Long newId = catagoryMapper.insertCatagoryReturnId(catagory);
        return newId;
    }

    @Override
    public void saveCatagory(PtGoodsCatagory catagory, Long[] mallIds, MultipartFile file) {
        if (catagory.getId() == null || catagory.getId().compareTo(0l) <= 0) {
            catagoryMapper.insertCatagoryReturnId(catagory);
            if (catagory.getParentId().compareTo(0L) > 0) {
                catagory.setPath(catagoryMapper.selectById(catagory.getParentId()).getPath() + catagory.getId() + ".");
            } else {
                catagory.setPath(catagory.getId() + ".");
            }
        }
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();
            int i = file.getOriginalFilename().lastIndexOf(".");
            String imageUrl =  Setting.CATAIMAGE + "/" + catagory.getId() + (i >= 0 ? name.substring(i) : ".jpg");
            catagory.setImageUrl(ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name()) + imageUrl);
            String path = ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name())+imageUrl;
            updateImg(path, file);
            catagoryMapper.updateById(catagory);
        }
        if(mallIds != null && mallIds.length > 0){
            for (Long mallId : mallIds){
                PtMallCatagory mallCatagory = new PtMallCatagory(mallId,catagory.getId());
                PtMallCatagory flag = mallCatagoryMapper.selectOne(mallCatagory);
                if (flag == null){
                    mallCatagoryMapper.insert(mallCatagory);
                }
            }
            mallCatagoryMapper.delete(new EntityWrapper<PtMallCatagory>().where("catagory_id = {0}",catagory.getId()).notIn("mall_id",mallIds));
        }
        catagoryMapper.updateById(catagory);
    }

    private File updateImg(String path, MultipartFile file) {
        try {
            ImgCompress imgCompress = new ImgCompress(file.getInputStream());
            InputStream inputStream = imgCompress.resize(100, 96);
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            File localFile = new File(path);
            if (!localFile.getParentFile().exists()) {
                localFile.getParentFile().mkdirs();
            }
            int index;
            byte[] bytes = new byte[1024];
            FileOutputStream downloadFile = new FileOutputStream(localFile);
            while ((index = inputStream.read(bytes)) != -1) {
                downloadFile.write(bytes, 0, index);
                downloadFile.flush();
            }
            downloadFile.close();
            inputStream.close();
            localFile.createNewFile();
            return localFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
