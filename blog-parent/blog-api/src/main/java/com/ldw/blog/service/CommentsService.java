package com.ldw.blog.service;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.params.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    /**
     * 根据登录用户 创建评论信息
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
