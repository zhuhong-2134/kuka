package com.camelot.kuka.backend.service;

import com.camelot.kuka.backend.model.MailMould;

import java.util.List;

/**
 * 邮件模板
 * @author 大老崔
 * @date 2020/02/17
 */
public interface MailMouldService {

    /**
     * 新增
     */
    public Integer insert(MailMould mailMould);

    /**
     * 删除
     */
    public Integer delete(int id);

    /**
     * 更新
     */
    public Integer update(MailMould mailMould);

    /**
     * 根据主键 id 查询
     */
    public MailMould findById(int id);

    /**
     * 列表查询
     */
    public List<MailMould> findAllList(int type);

}
