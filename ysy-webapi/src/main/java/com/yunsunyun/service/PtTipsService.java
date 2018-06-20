package com.yunsunyun.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.mapper.PtOrderMapper;
import com.yunsunyun.point.mapper.PtTipMapper;
import com.yunsunyun.point.po.PtTip;
import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class PtTipsService extends ServiceImpl<PtTipMapper, PtTip> {

    @Autowired
    private PtOrderMapper orderMapper;
    @Autowired
    private PtTipMapper tipMapper;

    /**
     * 查询当前登录用户在当前商城所接受的消息列表
     *
     * @param pageSize
     * @param pageNo
     * @param map
     * @param session  @return
     */
    @Transactional
    public ResponseMap listByMallIdAndMemberId(Integer pageSize, Integer pageNo, ResponseMap map, HttpSession session) throws Exception {
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        Long memberId = user.getId();
        Long mallId = (Long) session.getAttribute("mallId");
        Page<Map<String,Object>> page = new Page<>();
        if (pageSize != 0 || pageSize != null){
            page.setSize(pageSize);
        }
        if (pageNo != 0 || pageNo != null){
            page.setCurrent(pageNo);
        }
        page = selectMapsPage(page, new EntityWrapper<PtTip>().where("mall_id = {0}", mallId).and("receiver_id = {0}", memberId).and("is_deleted = 0").orderBy("send_time",false));
        for(int i = 0; i < page.getRecords().size(); i++){
            page.getRecords().get(i).put("orderStatus",orderMapper.selectById((Long) page.getRecords().get(i).get("orderId")).getOrderStatus());
        }
        //将所有消息设置成旧状态
        tipMapper.updateIsNew(mallId, memberId);
        map.setCode(200);
        map.setMsg("操作成功!");
        map.setTotalSize(page.getTotal());
        map.setData(page.getRecords());
        return map;
    }

    /**
     * 根据消息id将该消息置为已读状态
     *
     * @param map
     * @param id
     * @return
     */
    @Transactional
    public ResponseMap readTipById(ResponseMap map, Long id) throws Exception {
        PtTip tip = new PtTip();
        tip.setId(id);
        tip.setReadStatus(1);
        tip.setReceiveTime(System.currentTimeMillis());
        Integer result = tipMapper.updateById(tip);
        if (result <= 0) {
            map.setCode(500);
            map.setMsg("操作失败!");
        } else {
            map.setCode(200);
            map.setMsg("操作成功!");
        }
        return map;
    }

    /**
     * 查看当前登录会员在当前商城中的新消息数量
     *
     * @param map
     * @param session
     * @return
     */
    public ResponseMap getIsNewCountByMemberId(ResponseMap map, HttpSession session) throws Exception {
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        Long memberId = user.getId();
        Long mallId = (Long) session.getAttribute("mallId");
        HashMap<String,Object> countMap = new HashMap<>();
        Integer count = tipMapper.selectCount(new EntityWrapper<PtTip>().where("mall_id = {0}",mallId).and("receiver_id = {0}",memberId).and("is_new = 0"));
        countMap.put("count",count);
        map.setCode(200);
        map.setMsg("操作成功!");
        map.setData(countMap);
        return map;
    }

    /**
     * 员逻辑删除消息
     *
     * @param map
     * @param id
     * @return
     */
    public ResponseMap memberDeleteTipById(ResponseMap map, Long id) throws Exception {
        PtTip tip = new PtTip();
        tip.setId(id);
        tip.setIsDeleted(1);
        Integer result = tipMapper.updateById(tip);
        if (result <= 0) {
            map.setCode(500);
            map.setMsg("操作失败!");
        } else {
            map.setCode(200);
            map.setMsg("操作成功!");
        }
        return map;
    }
}
