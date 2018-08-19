/**
 * MIT License
 * Copyright (c) 2018 lihui
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.lihui.blog.controller;

import com.github.pagehelper.PageInfo;
import com.lihui.blog.business.entity.Comment;
import com.lihui.blog.business.enums.TemplateKeyEnum;
import com.lihui.blog.framework.object.ResponseVO;
import com.lihui.blog.util.ResultUtil;
import com.lihui.blog.business.enums.ResponseStatus;
import com.lihui.blog.business.service.BizCommentService;
import com.lihui.blog.business.service.MailService;
import com.lihui.blog.business.vo.CommentConditionVO;
import com.lihui.blog.framework.exception.ZhydCommentException;
import com.lihui.blog.framework.object.PageResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论管理
 *
 * @author lihui ()
 * @version 1.0
 * @website https://www.iotjike.com
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/comment")
public class RestCommentController {
    @Autowired
    private BizCommentService commentService;
    @Autowired
    private MailService mailService;

    @RequiresPermissions("comments")
    @PostMapping("/list")
    public PageResult list(CommentConditionVO vo) {
        PageInfo<Comment> pageInfo = commentService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    @RequiresPermissions("comment:reply")
    @PostMapping(value = "/reply")
    public ResponseVO reply(Comment comment) {
        try {
            commentService.commentForAdmin(comment);
        } catch (ZhydCommentException e){
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.success("成功");
    }

    @RequiresPermissions(value = {"comment:batchDelete", "comment:delete"}, logical = Logical.OR)
    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            commentService.removeByPrimaryKey(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 条评论");
    }

    @RequiresPermissions("comments")
    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.commentService.getByPrimaryKey(id));
    }

    @RequiresPermissions("comments")
    @PostMapping("/edit")
    public ResponseVO edit(Comment comment) {
        try {
            commentService.updateSelective(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("评论修改失败！");
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    /**
     * 审核
     *
     * @param comment
     * @return
     */
    @RequiresPermissions("comment:audit")
    @PostMapping("/audit")
    public ResponseVO audit(Comment comment, String contentText, Boolean sendEmail) {
        try {
            commentService.updateSelective(comment);
            if(!StringUtils.isEmpty(contentText)){
                comment.setContent(contentText);
                commentService.commentForAdmin(comment);
            }
            if(null != sendEmail && sendEmail){
                Comment commentDB = commentService.getByPrimaryKey(comment.getId());
                mailService.send(commentDB, TemplateKeyEnum.TM_COMMENT_AUDIT, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("评论审核失败！");
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

    /**
     * 正在审核的
     *
     * @param comment
     * @return
     */
    @RequiresUser
    @PostMapping("/listVerifying")
    public ResponseVO listVerifying(Comment comment) {
        return ResultUtil.success(null, commentService.listVerifying(10));
    }

}
