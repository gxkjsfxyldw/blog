package com.ldw.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ldw.blog.dao.mapper.CommentMapper;
import com.ldw.blog.dao.pojo.Comment;
import com.ldw.blog.dao.pojo.SysUser;
import com.ldw.blog.service.CommentsService;
import com.ldw.blog.service.SysUserService;
import com.ldw.blog.utils.UserThreadLocal;
import com.ldw.blog.vo.CommentVo;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.UserVo;
import com.ldw.blog.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;

    //评论功能 创建评论
    @Override
    public Result comment(CommentParam commentParam) {

        SysUser sysUser = UserThreadLocal.get(); //这里存储有登录用户信息
        System.out.println("登录用户"+sysUser.getNickname());

        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());

        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }
    //查看评论
    @Override
    public Result commentsByArticleId(Long id) {
        /*
            1.根据文章id查询评论列表 从 comment 表中查询
            2.根据作者的id查询作者的信息
            3.判断 如果 level =1 要去查询它的有没有子评论
            4.如果有 子评论，根据评论id进行查询 （parentId）
         */

        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVos=copylist(comments);
        //将pojo的数据拷贝成Vo
        return Result.success(commentVos);
    }

    private List<CommentVo> copylist(List<Comment> comments) {
        List<CommentVo> commentVos=new ArrayList<>();
        for (Comment comment : comments) {
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo=new CommentVo();
        //这个的spring自带的BeanUtils的工具类，将pojo与vo属性相同的拷贝到vo中
        BeanUtils.copyProperties(comment,commentVo);
        //缓存新改 用pojo里面的转换为string再存储
        commentVo.setId(String.valueOf(comment.getId()));
        //作者信息
        Long authorId=comment.getAuthorId();
        UserVo userVoId = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVoId);
        //子评论 只有level  1才有子评论
        Integer level = comment.getLevel();
        if(level==1){
            Long id=comment.getId();
            List<CommentVo> commentVos=findCommentsByParentId(id);
            commentVo.setChildrens(commentVos);
        }
        // toUser 就是给谁评论的
        if(level>1){
            Long toUid = comment.getToUid();
           UserVo touser= this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(touser);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return copylist(commentMapper.selectList(queryWrapper));
    }

}
