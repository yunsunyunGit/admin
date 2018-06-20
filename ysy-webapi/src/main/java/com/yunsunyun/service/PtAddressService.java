package com.yunsunyun.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.mapper.PtAddressMapper;
import com.yunsunyun.point.po.PtAddress;
import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PtAddressService extends ServiceImpl<PtAddressMapper, PtAddress> {

    @Autowired
    private PtAddressMapper addressMapper;

    /**
     * 根据id查询收货地址详细信息
     *
     * @param map
     * @param id
     * @return
     */
    public ResponseMap getAddressById(ResponseMap map, Long id) throws Exception {
        PtAddress address = null;
        if (id == 0) {
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            address = selectOne(new EntityWrapper<PtAddress>().where("is_default = 1").eq("member_id",user.getId()));
        } else {
            address = addressMapper.selectById(id);
        }
        if (address == null) {
            map.setCode(404);
            map.setMsg("数据不存在!");
        } else {
            map.setCode(200);
            map.setMsg("操作成功");
            map.setData(address);
        }
        return map;
    }

    /**
     * 获取当前登录会员的收货地址列表
     *
     * @param pageSize
     * @param pageNo
     * @param map
     * @return
     */
    public ResponseMap ajaxAddressList(Integer pageSize, Integer pageNo, ResponseMap map) throws Exception {
        Page<PtAddress> page = new Page<>();
        if (pageSize != 0 || pageSize != null){
            page.setSize(pageSize);
        }
        if (pageNo != 0 || pageNo != null){
            page.setCurrent(pageNo);
        }
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        Long memberId = user.getId();
        page = selectPage(page, new EntityWrapper<PtAddress>().where("member_id = {0}", memberId).and("is_deleted = 0"));
        if (page.getRecords() != null && page.getRecords().size() > 0) {
            map.setCode(200);
            map.setMsg("操作成功");
            map.setTotalSize(page.getTotal());
            map.setData(page.getRecords());
        } else {
            map.setCode(404);
            map.setMsg("该会员没有收货地址!");
        }
        return map;
    }

    /**
     * 新增收货地址
     *
     * @param address
     * @param map
     * @return
     */
    @Transactional
    public ResponseMap createAddress(PtAddress address, ResponseMap map) throws Exception {
        if (address == null) {
            map.setCode(500);
            map.setMsg("新增收货地址信息不能为空!");
            return map;
        }
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        address.setMemberId(user.getId());
        Integer result = addressMapper.insert(address);
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
     * 修改指定收货地址
     *
     * @param address
     * @param map
     * @return
     */
    @Transactional
    public ResponseMap updateAddressById(PtAddress address, ResponseMap map) throws Exception {
        if (address.getId() == null || address.getId() <= 0) {
            map.setCode(500);
            map.setMsg("修改的收货地址id不存在!");
            return map;
        }
        Integer result = addressMapper.updateEntityById(address);
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
     * 设置新的默认收货地址
     *
     * @param id
     * @param map
     * @return
     */
    @Transactional
    public ResponseMap setDefaultAddress(Long id, ResponseMap map) throws Exception {
        if (id == null || id <= 0) {
            map.setCode(500);
            map.setMsg("收货地址id不存在!");
            return map;
        }
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        addressMapper.cannelDefault(user.getId());
        PtAddress address = new PtAddress();
        address.setId(id);
        address.setMemberId(user.getId());
        Integer result = addressMapper.setDefault(address);
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
