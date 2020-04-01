package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.CommentTypeDao;
import com.camelot.kuka.backend.model.CommentType;
import com.camelot.kuka.backend.service.CommentTypeService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.model.backend.comment.resp.CommentTypeResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.PrincipalEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Description: [评论分类业务层]</p>
 * Created on 2020/2/5
 *
 *
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("commentTypeService")
public class CommentTypeServiceImpl implements CommentTypeService {

    @Resource
    private CommentTypeDao commentTypeDao;
    @Resource
    private CodeGenerateUtil codeGenerateUtil;

    @Override
    public Result<List<CommentTypeResp>> queryList() {
        List<CommentType> list = commentTypeDao.queryList();
        return Result.success(BeanUtil.copyBeanList(list, CommentTypeResp.class));
    }

    @Override
    public Result addCommentType(CommentTypeResp req, String loginUserName) {
        CommentType commentType = new CommentType();
        // 获取原有数据最大的
        Integer maxCode = commentTypeDao.queryMaxCode();
        if (maxCode == null) {
            commentType.setCode(0);
        } else {
            commentType.setCode(maxCode + 1);
        }
        commentType.setName(req.getName());
        commentType.setDes(req.getName());
        // 获取ID
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_COMMENT_TYPE);
        commentType.setId(id);
        int con = commentTypeDao.insertBatch(Arrays.asList(commentType));
        if (con == 0) {
            return Result.error("新增失败, 请联系管理员");
        }
        return Result.success(commentType.getCode());
    }

    @Override
    public Result deleteCommentType(CommonReq req, String loginUserName) {
        int con = commentTypeDao.deleteCommentType(req.getId());
        if (con == 0) {
            return Result.error("删除失败, 请联系管理员");
        }
        return Result.success();
    }
}
