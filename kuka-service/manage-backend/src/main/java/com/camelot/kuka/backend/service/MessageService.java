package com.camelot.kuka.backend.service;

import com.camelot.kuka.model.backend.message.req.MessageReq;
import com.camelot.kuka.model.backend.message.resp.MessageResp;
import com.camelot.kuka.model.common.Result;

import java.util.List;

/**
 * <p>Description: [消息]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
public interface MessageService {

    /***
     * <p>Description:[列表查询]</p>
     * Created on 2020/1/20
     * @param req
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    Result<List<MessageResp>> findList(MessageReq req);

    /***
     * <p>Description:[修改]</p>
     * Created on 2020/1/20
     * @param req
     * @param loginUserName
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    Result updateMessage(MessageReq req, String loginUserName);


    /***
     * <p>Description:[修改]</p>
     * Created on 2020/1/20
     * @param req
     * @param loginUserName
     * @return com.camelot.kuka.model.common.PageResult
     * @author 谢楠
     */
    Result addMessage(MessageReq req, String loginUserName);
}
