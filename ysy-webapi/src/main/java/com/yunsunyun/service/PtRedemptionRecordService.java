package com.yunsunyun.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.mapper.PtRedemptionRecordMapper;
import com.yunsunyun.point.po.PtRedemptionRecord;
import com.yunsunyun.point.vo.PointRecordVo;
import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Service
public class PtRedemptionRecordService extends ServiceImpl<PtRedemptionRecordMapper, PtRedemptionRecord> {

    @Autowired
    private PtRedemptionRecordMapper recordMapper;
    /**
     * 获取当前登录会员在当前商城中产生的积分消费记录
     *
     * @param map
     * @param pageSize
     *@param pageNo
     * @param date
     * @param session   @return
     */
    public ResponseMap ajaxRecordList(ResponseMap map, Integer pageSize, Integer pageNo, String date, HttpSession session) throws Exception{
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        Integer offset = (pageNo - 1) * pageSize;
        Long mallId = (Long) session.getAttribute("mallId");
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        List<PointRecordVo> recordVos = recordMapper.ajaxRecordList(offset,pageSize,mallId,user.getId(),date);
        Double pointTotal = (Double) session.getAttribute("totalIntegral");
        HashMap<String,Object> dataMap = new HashMap<>();
        dataMap.put("pointTotal",pointTotal);
        dataMap.put("records",recordVos);
        map.setCode(200);
        map.setMsg("操作成功!");
        map.setTotalSize(recordMapper.getRecordListSize(mallId,user.getId(),date));
        map.setData(dataMap);
        return map;
    }
}
