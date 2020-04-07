package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.MailMould;
import com.camelot.kuka.model.backend.mailmould.req.MailMouldPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>Description: [消息模板接口]</p>
 * Created on 2020/2/17
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface MailMouldDao {

    /**
     * 列表查询
     * @param req
     * @return
     */
    List<MailMould> pageList(@Param("entity") MailMouldPageReq req);

    /**
     * 单个查询
     * @param query
     * @return
     */
    MailMould findInfo(@Param("entity") MailMould query);

    /***
     * 批量新增
     * @param mailMould
     * @return
     */
    int addBatch(@Param("list") List<MailMould> mailMould);

    /**
     * 修改
     * @param mailMould
     * @return
     */
    int update(MailMould mailMould);


}
