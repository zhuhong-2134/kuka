package com.camelot.kuka.backend.dao;

import com.camelot.kuka.backend.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: [消息管理DAO]</p>
 * Created on 2020/1/19
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Mapper
public interface MessageDao {

    /***
     * <p>Description:[列表查询]</p>
     * Created on 2020/2/4
     * @param query
     * @return list
     *
     */
    List<Message> findList(@Param("entity") Message query);

    /***
     * <p>Description:[列表查询]</p>
     * Created on 2020/2/4
     * @param id
     * @return list
     *
     */
    Message queryById(@Param("id") Long id);

    /***
     * <p>Description:[批量新增]</p>
     * Created on 2020/2/4
     * @param list
     * @return int
     *
     */
    int addBatch(@Param("list") List<Message> list);

    /***
     * <p>Description:[批量新增]</p>
     * Created on 2020/2/4
     * @param message
     * @return int
     *
     */
    int update(Message message);
}
