package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.model.Comment;
import com.camelot.kuka.backend.service.CommentService;
import com.camelot.kuka.model.enums.DeleteEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Description: [评论实现]</p>
 * Created on 2020/2/5
 *
 * @author <a href="mailto: xienan@camelotchina.com">谢楠</a>
 * @version 1.0
 * Copyright (c) 2020 北京柯莱特科技有限公司
 */
@Slf4j
@Service("commentService")
public class CommentServiceImpl implements CommentService {


    @Override
    public List<Comment> queryByAppId(Long appId) {

        Comment c1 = new Comment();
        c1.setAppId(appId);
        c1.setId(1L);
        c1.setDelState(DeleteEnum.NO);
        c1.setCommentDesc("一个是的范德萨不是");
        Comment c2 = new Comment();
        c2.setAppId(appId);
        c2.setId(1L);
        c2.setDelState(DeleteEnum.NO);
        c2.setCommentDesc("一个是的范德萨不是");
        List<Comment> comments = Arrays.asList(c1, c2);
        return comments;
    }
}
