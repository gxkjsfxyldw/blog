package com.ldw.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldw.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag>findTagsByArticleById(Long articleId);

    /**
     * 查询最热标签 前limit条
     * @param limit
     * @return
     */
    List<Long> findHotTagIds(int limit);
    //根据最热标签id 查找最热标签name
    List<Tag> findTagByTagIds(List<Long> tagIds);
}
