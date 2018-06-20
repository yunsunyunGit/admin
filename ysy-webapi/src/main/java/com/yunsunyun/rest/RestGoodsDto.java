package com.yunsunyun.rest;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtGoods;
import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.service.PtGoodsService;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/v1/goods")
public class RestGoodsDto {

    private static Logger logger = LoggerFactory.getLogger(RestGoodsDto.class);

    @Autowired
    private PtGoodsService goodsService;

    /**
     * 分页查询商品列表
     *
     * @param pageNo     每页数量
     * @param pageSize   当前页码
     * @param goodsType  商品推荐分类（字典表首页推荐分类子项的dic_key）
     * @param catagoryId 商品所属分类
     * @param keyword    搜索关键字（目前只作为商品名）
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseMap goodsList(Integer pageNo, Integer pageSize,
                                 @RequestParam(required = false) String goodsType,
                                 @RequestParam(required = false) Integer catagoryId, @RequestParam(required = false) String keyword, HttpSession session) {
        ResponseMap map = new ResponseMap();
        try {
            Page<PtGoods> page = new Page<>();
            if (pageSize != 0 || pageSize != null) {
                page.setSize(pageSize);
            }
            if (pageNo != 0 || pageNo != null) {
                page.setCurrent(pageNo);
            }
            page = goodsService.selectListWithParam(page, goodsType, catagoryId, keyword, session);
            map.setCode(200);
            map.setMsg("操作成功");
            map.setTotalSize(page.getTotal());
            map.setData(page.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取商品列表失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 查看商品详情,会员总积分不够则提示会员去赚取积分
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseMap getGoodsById(@PathVariable(value = "id") Long id, HttpSession session) {
        ResponseMap map = new ResponseMap();
        try {
            map = goodsService.queryGoodsAndPics(map, id, session);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取商品详情失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 当前用户兑换商品
     *
     * @param id
     * @param goodsNum
     * @param addressId
     * @param session
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseMap buyGoods(@PathVariable("id") Long id, Integer goodsNum, Long addressId,HttpSession session) {
        ResponseMap map = new ResponseMap();
        try {
            map = goodsService.convertGoods(map,id,goodsNum,addressId,session);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("购买商品失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }
}
