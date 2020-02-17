package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.MailMould;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
/**
 * <p>Description: [消息模板接口]</p>
 * Created on 2020/2/17
 *
 * @author <a href="mailto: cuichunsong@camelotchina.com">崔春松</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */

@Mapper
public interface MailMouldDao {
    /***
     * 新增
     * @param mailMould
     * @return
     */
    int add(MailMould mailMould);

    /**
     * 修改
     * @param mailMould
     * @return
     */
    int update(MailMould mailMould);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    MailMould findById(int id);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<MailMould> findAllList(int type);
}
