package com.camelot.kuka.backend.controller;

import com.alibaba.fastjson.JSON;
import com.camelot.kuka.backend.service.MessageService;
import com.camelot.kuka.common.controller.BaseController;
import com.camelot.kuka.common.utils.AppUserUtil;
import com.camelot.kuka.model.backend.message.req.MessageReq;
import com.camelot.kuka.model.backend.message.resp.MessageResp;
import com.camelot.kuka.model.common.EnumVal;
import com.camelot.kuka.model.common.PageResult;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.application.AppTypeEnum;
import com.camelot.kuka.model.enums.backend.MessageStatusEnum;
import com.camelot.kuka.model.enums.backend.MessageTypeEnum;
import com.camelot.kuka.model.enums.backend.SkilledAppEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [消息控制层]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@RestController
@Api(value = "消息API", tags = { "消息接口" })
public class MessageController extends BaseController {

    @Resource
    private MessageService messageService;


    /***
     * <p>Description:[枚举查询]</p>
     * Created on 2020/1/20
     * @param
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/message/queryEnum")
    public PageResult queryEnum(){
        PageResult page = new PageResult();
        page.putEnumVal("typeEnum", EnumVal.getEnumList(MessageTypeEnum.class));
        page.putEnumVal("statusEnum", EnumVal.getEnumList(MessageStatusEnum.class));
        return page;
    }

    /***
     * <p>Description:[列表查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/message/findList")
    public Result<List<MessageResp>> findList(MessageReq req){
        try {
            Result<List<MessageResp>> resp  = messageService.findList(req);
            resp.putEnumVal("classTypeEnum", EnumVal.getEnumList(AppTypeEnum.class));
            resp.putEnumVal("appRangeEnum", EnumVal.getEnumList(SkilledAppEnum.class));
            return resp;
        } catch (Exception e) {
            log.error("\n 消息模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "findList", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    @PostMapping("/message/update")
    public Result updateMessage(MessageReq req){
        try {
            String loginUserName = AppUserUtil.getLoginUserName();
            if (null == loginUserName) {
                return Result.error("用户未登录");
            }
            Result resp  = messageService.updateMessage(req, loginUserName);
            return resp;
        } catch (Exception e) {
            log.error("\n 消息模块, \n 方法:{}, \n 参数:{}, \n 错误信息:{}", "updateMessage", JSON.toJSONString(req), e);
            return PageResult.error("网络异常, 请稍后再试");
        }
    }
}
