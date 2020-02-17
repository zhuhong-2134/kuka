package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.MailMouldDao;
import com.camelot.kuka.backend.model.MailMould;
import com.camelot.kuka.backend.service.MailMouldService;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.enums.PrincipalEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: [消息模板]</p>
 * Created on 2020/2/17
 *
 * @author <a href="mailto: cuichunsong@camelotchina.com">崔春松</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */

@Slf4j
@Service
public class MailMouldServiceImpl implements MailMouldService {

    @Resource
    private MailMouldDao mailMouldDao;

    @Resource
    private CodeGenerateUtil codeGenerateUtil;

    @Override
    public Integer insert(MailMould mailMould) {
        Date date = new Date();
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_MAIL_MOULD);
        mailMould.setId(id);
        mailMould.setCreateTime(date);
        mailMould.setUpdateTime(date);
        int add = mailMouldDao.add(mailMould);
        return add;
    }

    @Override
    public Integer delete(int id) {
        int delete = mailMouldDao.delete(id);
        return delete;
    }

    @Override
    public Integer update(MailMould mailMould) {
        mailMould.setUpdateTime(new Date());
        int update = mailMouldDao.update(mailMould);
        return update;
    }

    @Override
    public MailMould findById(int id) {
        MailMould byId = mailMouldDao.findById(id);
        return byId;
    }

    @Override
    public List<MailMould> findAllList(int type) {
        List<MailMould> allList = mailMouldDao.findAllList(type);
        return allList;
    }
}
