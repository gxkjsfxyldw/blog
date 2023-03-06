package com.ldw.blog.service;

import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TagService {
    List<TagVo> findTagByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * 查找所有标签
     * @return
     */
    Result findAll();

    /**
     * 查找标签详情
     * @return
     */
    Result findAlldetail();

    /**
     * 查找文章详情的标签
     * @param id
     * @return
     */
    Result findAlldetailByid(Long id);
}
