package com.camelot.kuka.backend.feign.user;

import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.req.UserReq;
import com.camelot.kuka.model.user.resp.UserResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: [用户信息feign]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@FeignClient("user-center")
public interface UserClient {

    /***
     * <p>Description:[通过ID获取用户]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @GetMapping(value = "/users/client/queryById")
    public Result<UserResp> clientById(@RequestParam("id") Long id);

    /***
     * <p>Description:[修改用户信息]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/client/update")
    public Result clientUpDATE(@RequestBody UserReq req);

    /***
     * <p>Description:[根据地址编码返回名称]</p>
     * Created on 2020/2/4
     * @param codes
     * @return key code value 名称
     * @author 谢楠
     */
    @PostMapping("/address/queryAddressMap")
    public Result<Map<String, String>> queryAddressMap(@RequestBody List<String> codes);
}
