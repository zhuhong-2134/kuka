package com.camelot.kuka.backend.feign.user;

import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.user.resp.UserResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

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
     * <p>Description:[单条查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/users/queryById")
    Result<UserResp> queryById(CommonReq req);

}
