package com.camelot.kuka.user.feign;

import com.camelot.kuka.model.backend.supplier.resp.SupplierResp;
import com.camelot.kuka.model.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>Description: [邮件模板]</p>
 * Created on 2020/2/5
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@FeignClient("manage-backend")
public interface MangeBackenCilent {

    /**
     *
     * @param userName
     * @return
     */
    @GetMapping(value = "/supplier/queryByCreateName")
    Result<SupplierResp> queryByCreateName(@RequestParam("userName") String userName);
}
