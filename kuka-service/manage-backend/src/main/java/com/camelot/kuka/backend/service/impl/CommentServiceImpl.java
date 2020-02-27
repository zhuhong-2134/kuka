package com.camelot.kuka.backend.service.impl;

import com.camelot.kuka.backend.dao.ApplicationDao;
import com.camelot.kuka.backend.dao.CommentDao;
import com.camelot.kuka.backend.dao.OrderDao;
import com.camelot.kuka.backend.dao.OrderDetailedDao;
import com.camelot.kuka.backend.model.Comment;
import com.camelot.kuka.backend.model.Order;
import com.camelot.kuka.backend.model.OrderDetailed;
import com.camelot.kuka.backend.service.CommentService;
import com.camelot.kuka.common.utils.BeanUtil;
import com.camelot.kuka.common.utils.CodeGenerateUtil;
import com.camelot.kuka.common.utils.StringUtils;
import com.camelot.kuka.model.backend.comment.req.CommentPageReq;
import com.camelot.kuka.model.backend.comment.req.CommentReq;
import com.camelot.kuka.model.backend.comment.resp.CommentResp;
import com.camelot.kuka.model.backend.order.resp.OrderDetailedResp;
import com.camelot.kuka.model.common.CommonReq;
import com.camelot.kuka.model.common.Result;
import com.camelot.kuka.model.enums.DeleteEnum;
import com.camelot.kuka.model.enums.PrincipalEnum;
import com.camelot.kuka.model.enums.comment.CommentStatusEnum;
import com.camelot.kuka.model.user.LoginAppUser;
import com.camelot.kuka.model.user.menu.resp.MenuTreeResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private CommentDao commentDao;
    @Resource
    private OrderDetailedDao orderDetailedDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private ApplicationDao applicationDao;
    @Resource
    private CodeGenerateUtil codeGenerateUtil;

    @Override
    public List<Comment> pageList(CommentPageReq req) {
        req.setDelState(DeleteEnum.NO);
        req.setQueryTypeCode(null != req.getQueryType() ? req.getQueryType().getCode() : null);
        List<Comment> list = commentDao.pageList(req);
        // 订单总价, 订单明细
        getOrderDetaileList(list);
        return list;
    }

    /**
     * 获取明细信息
     * @param list
     */
    private void getOrderDetaileList(List<Comment> list){
        if (list.isEmpty()) {
            return;
        }
        Long[] ids = list.stream().map(Comment::getOrderId).toArray(Long[]::new);
        // 获取明细信息
        List<OrderDetailed> deailList = orderDetailedDao.selectByOrderIds(ids);
        // 获取订单
        List<Order> orders = orderDao.queryByIds(ids);
        for (Comment comment : list) {
            List<OrderDetailed> newList = new ArrayList<>();
            for (OrderDetailed orderDetailed : deailList) {
                if (comment.getOrderId().compareTo(orderDetailed.getOrderId()) == 0) {
                    newList.add(orderDetailed);
                }
            }
            // 明细
            comment.setDetaileList(BeanUtil.copyBeanList(newList, OrderDetailedResp.class));
            // 订单总价
            for (Order order : orders) {
                if (comment.getOrderId().compareTo(order.getId()) == 0) {
                    comment.setSunPrice(order.getSunPrice());
                    break;
                }
            }
            if (StringUtils.isNotEmpty(comment.getCommentUrl())) {
                // 前端要的数组图片
                comment.setCommentUrls(comment.getCommentUrl().split(","));
            }
        }
    }


    @Override
    public List<Comment> queryByAppId(Long appId) {

        Comment c1 = new Comment();
        c1.setAppId(appId);
        c1.setId(1L);
        c1.setDelState(DeleteEnum.NO);
        c1.setCommentDesc("一个是的范德萨不是");
        c1.setCreateBy("熊大");
        c1.setCreateTime(new Date());
        c1.setCommentUrl("https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg,https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg,https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg");
        c1.setCommentUrls(c1.getCommentUrl().split(","));
        c1.setPhotoUrl("https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg");
        Comment c2 = new Comment();
        c2.setAppId(appId);
        c2.setId(1L);
        c2.setDelState(DeleteEnum.NO);
        c2.setCommentDesc("一个是的范德萨不是");
        c2.setCreateBy("熊大");
        c2.setCreateTime(new Date());
        c2.setCommentUrl("https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg,https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg,https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg");
        c2.setCommentUrls(c1.getCommentUrl().split(","));
        c2.setPhotoUrl("https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg");
        List<Comment> comments = Arrays.asList(c1, c2);
        return comments;
    }

    @Override
    public List<Comment> queryByOrderIds(Long[] appId) {
        return commentDao.queryByOrderIds(appId);
    }

    @Override
    public Result delComment(CommonReq req, String loginUserName) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        Comment com = new Comment();
        com.setId(req.getId());
        com.setDelState(DeleteEnum.YES);
        com.setUpdateBy(loginUserName);
        com.setUpdateTime(new Date());
        int con = commentDao.update(com);
        if (con == 0) {
            Result.error("删除失败, 请联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result toExamine(CommentReq req, String loginUserName) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        Comment com = new Comment();
        com.setId(req.getId());
        com.setStatus(req.getStatus());
        com.setUpdateBy(loginUserName);
        com.setCommentType(req.getCommentType());
        com.setUpdateTime(new Date());
        com.setAdoptTime(new Date());
        int con = commentDao.update(com);
        if (con == 0) {
            Result.error("删除失败, 请联系管理员");
        }
        return Result.success();
    }

    @Override
    public Result<CommentResp> queryById(CommonReq req) {
        if (null == req || null == req.getId()) {
            return Result.error("主键不能为空");
        }
        Comment query = new Comment();
        query.setId(req.getId());
        query.setDelState(DeleteEnum.NO);
        Comment comment = commentDao.queryInfo(query);
        if (null == comment) {
            return Result.error("数据获取失败, 刷新后重试");
        }
        if (StringUtils.isNotEmpty(comment.getCommentUrl())) {
            // 前端要的数组图片
            comment.setCommentUrls(comment.getCommentUrl().split(","));
        }
        return Result.success(BeanUtil.copyBean(comment, CommentResp.class));
    }

    @Override
    public Result addComment(CommentReq req, LoginAppUser loginUser) {
        if (null == req || null == req.getOrderId()) {
            return Result.error("订单ID不能为空");
        }
        if (null == req.getOrderNo()) {
            return Result.error("订单编号不能为空");
        }
        if (null == req.getAppId()) {
            return Result.error("产品ID不能为空");
        }
        if (StringUtils.isBlank(req.getCommentDesc())) {
            return Result.error("评论不能为空");
        }
        Comment comment = BeanUtil.copyBean(req, Comment.class);
        // 获取ID
        Long id = codeGenerateUtil.generateId(PrincipalEnum.MANAGE_COMMENT);
        comment.setId(id);
        comment.setStatus(CommentStatusEnum.WAIT);
        comment.setCreateBy(loginUser.getUsername());
        comment.setCreateTime(new Date());
        comment.setUserId(loginUser.getId());
        comment.setPhotoUrl(loginUser.getPhotoUrl());
        int con = commentDao.addBatch(Arrays.asList(comment));
        if (con == 0) {
            return Result.error("新增失败, 联系管理员");
        }
        return Result.success(con);
    }

}
