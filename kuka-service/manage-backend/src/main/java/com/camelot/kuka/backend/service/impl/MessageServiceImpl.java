package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.MessageDao;
import com.camelot.kuka.backend.model.Message;
import com.camelot.kuka.backend.service.MessageService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.model.backend.application.req.AppPageReq;
import com.camelot.kuka.model.backend.message.req.MessageReq;
import com.camelot.kuka.model.backend.message.resp.MessageResp;
import com.camelot.kuka.model.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: [消息业务层]</p>
 * Created on 2020/1/19
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDao;

    @Override
    public Result<List<MessageResp>> findList(MessageReq req) {
        Message qeru = BeanUtil.copyBean(req, Message.class);
        List<Message> list = messageDao.findList(qeru);
        return Result.success(BeanUtil.copyBeanList(list, MessageResp.class));
    }
}
