package com.ldw.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ldw.blog.dao.mapper.TagMapper;
import com.ldw.blog.dao.pojo.Tag;
import com.ldw.blog.service.TagService;
import com.ldw.blog.vo.ArticleVo;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    public TagVo cope(Tag tag){
        TagVo tagVo=new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        //将数据中的字段和Vo对象中的字段数据进行传递
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }

    public List<TagVo> copeList(List<Tag> tagList){
        List<TagVo> TagVolist = new ArrayList<>();
        for (Tag tag : tagList) {
            TagVolist.add(cope(tag));
        }
        return TagVolist;
    }

    @Override
    public List<TagVo> findTagByArticleId(Long articleId) {
       //mybatisplus 无法进行多表查询,所以需要自定义一个mapper.xml进行多表查询
        List<Tag>tags= tagMapper.findTagsByArticleById(articleId);//传入标签id
        return copeList(tags);//将数据进行转换，把pojo转换成vo
    }

    /**最热标签
     * 1.标签所拥有的文章数量最多，即最热标签
     * 2.查询，根据toa_id 分组，计数，从大到小，排序，取前 limit个
     */
    @Override
    public Result hots(int limit) {
        /*
        *1.标签所拥有的文章数量最多最热标签
        *2.查询根据tag_id 分组计数，从大到小排列取前limit个
        */
        List<Long> tagIds = tagMapper.findHotTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)){ //判断最热标签内容是否为空
            return  Result.success(Collections.emptyList());
        }
        //需求的是tagId,和tagName --> tag对象
        //select * from tag where id in (1,2,3)
        List<Tag> taglist = tagMapper.findTagByTagIds(tagIds);
        return Result.success(taglist);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);//只查找这两个属性
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copeList(tags));
    }

    @Override
    public Result findAlldetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copeList(tags));
    }

    @Override
    public Result findAlldetailByid(Long id) {
        Tag tag=tagMapper.selectById(id);
        return Result.success(copy(tag));
    }

    private Object copy(Tag tag) {
        TagVo tagVo=new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo>copylist(List<Tag> tagList){
        List<TagVo>tagVos=new ArrayList<>();
        for (Tag tag : tagList) {
            tagVos.add(cope(tag));
        }
        return tagVos;
    }

}
