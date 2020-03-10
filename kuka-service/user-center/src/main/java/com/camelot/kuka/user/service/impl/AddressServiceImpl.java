package com.camelot.kuka.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.resp.AddressTreeResp;
import com.camelot.kuka.user.dao.AddressDao;
import com.camelot.kuka.user.model.Address;
import com.camelot.kuka.user.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressDao addressDao;


    @Override
    public void queryAddress() {
        List<Address> addresses = addressDao.selectAll();
        List<AddressTreeResp> resps = new ArrayList<>();
        for (Address address : addresses) {
            AddressTreeResp resp = new AddressTreeResp();
            resp.setId(address.getAreaid().longValue());
            resp.setCode(address.getAreacode());
            resp.setName(address.getAreaname());
            resp.setParentId(address.getParentid().longValue());
            resps.add(resp);
        }
        List<AddressTreeResp> orgTree = getOrgTree(resps, -1L);
        System.out.println(JSON.toJSONString(orgTree));
    }

    @Override
    public Result<Map<String, String>> queryAddressMap(List<String> codes) {
        List<Address> addresses = addressDao.qeuryListByCodes(codes);
        Map<String, String> map = new HashMap<>();
        for (Address address : addresses) {
            map.put(address.getAreacode(), address.getAreaname());
        }
        return Result.success(map);
    }


    /***
     * <p>
     * Description:[循环数据]
     * </p>
     * Created on 2019/11/12
     * @param treeList
     * @param pid
     * @return java.util.List<com.fehorizon.commonService.model.saas.resp.SysOrgTreeResp>
     * @author 谢楠
     */
    public List<AddressTreeResp> getOrgTree(List<AddressTreeResp> treeList, Long pid) {
        List<AddressTreeResp> result = new ArrayList<>();
        treeList = treeList.stream().filter(a -> a != null).collect(Collectors.toList());
        for (AddressTreeResp menuTreeResp : treeList) {
            if (menuTreeResp.getParentId().equals(pid)) {
                AddressTreeResp deptScopeMode = new AddressTreeResp();
                deptScopeMode.setId(menuTreeResp.getId());
                deptScopeMode.setParentId(menuTreeResp.getParentId());
                deptScopeMode.setName(menuTreeResp.getName());
                deptScopeMode.setParentId(menuTreeResp.getParentId());
                deptScopeMode.setCode(menuTreeResp.getCode());
                List<AddressTreeResp> temp = getOrgTree(treeList, menuTreeResp.getId());
                if (temp.size() > 0) {
                    deptScopeMode.setChildren(temp);
                }
                result.add(deptScopeMode);
            }
        }
        return result;
    }
}
