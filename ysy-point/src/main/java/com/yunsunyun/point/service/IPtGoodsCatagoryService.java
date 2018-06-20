package com.yunsunyun.point.service;

import com.yunsunyun.point.po.PtGoodsCatagory;
import com.baomidou.mybatisplus.service.IService;
import com.yunsunyun.xsimple.api.common.JsTree;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface IPtGoodsCatagoryService extends IService<PtGoodsCatagory> {

    /**
     * 获取分类树
     * @return
     */
    List<JsTree> getCatagoryTree();

    /**
     * 添加一条分类实例并返回主键
     * @param catagory
     * @return
     */
    Long insertCatagoryReturnId(PtGoodsCatagory catagory);

    /**
     * 保存分类信息
     */
    void saveCatagory(PtGoodsCatagory catagory, Long[] mallIds, MultipartFile file);
}
