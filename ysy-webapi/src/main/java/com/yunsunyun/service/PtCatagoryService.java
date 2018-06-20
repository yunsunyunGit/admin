package com.yunsunyun.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.mapper.PtGoodsCatagoryMapper;
import com.yunsunyun.point.mapper.PtMallCatagoryMapper;
import com.yunsunyun.point.po.PtGoodsCatagory;
import com.yunsunyun.point.po.PtMallCatagory;
import com.yunsunyun.point.vo.ResponseMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class PtCatagoryService extends ServiceImpl<PtGoodsCatagoryMapper, PtGoodsCatagory> {

    @Autowired
    private PtMallCatagoryMapper mallCatagoryMapper;
    @Autowired
    private PtGoodsCatagoryMapper catagoryMapper;

    /**
     * 获取分类列表
     *
     * @param map
     * @param keyword
     * @param session
     * @return
     */
    public ResponseMap ajaxDataList(ResponseMap map, String keyword, HttpSession session) throws Exception {
        Long mallId = (Long) session.getAttribute("mallId");
        List<PtMallCatagory> mallCatagoryList = mallCatagoryMapper.selectList(new EntityWrapper<PtMallCatagory>().where("mall_id = {0}", mallId));

        if (mallCatagoryList == null || mallCatagoryList.size() <= 0){
            map.setCode(500);
            map.setMsg("此商城暂无分类信息!");
            return map;
        }

        List<Long> cataIds = new ArrayList<>();
        for (PtMallCatagory mallCatagory : mallCatagoryList){
            cataIds.add(mallCatagory.getCatagoryId());
        }

        EntityWrapper<PtGoodsCatagory> wrapper = new EntityWrapper<>();
        wrapper.where("is_show = 1");
        wrapper.and("is_deleted = 0");
        if (StringUtils.isNotEmpty(keyword)){
            wrapper.like("name",keyword);
        }
        wrapper.in("id",cataIds);
        wrapper.orderBy("seq");
        List<PtGoodsCatagory> catagoryList = catagoryMapper.selectList(wrapper);
        map.setCode(200);
        map.setMsg("操作成功!");
        map.setData(catagoryList);
        return map;
    }
}
