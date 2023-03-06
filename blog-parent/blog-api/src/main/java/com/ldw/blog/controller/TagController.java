package com.ldw.blog.controller;

import com.ldw.blog.common.cache.Cache;
import com.ldw.blog.service.TagService;
import com.ldw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 首页最热标签
     * @return
     */
    //最热标签 tas/hot
    @GetMapping("/hot")
    //统一缓存处理
   // @Cache(expire = 5 * 60 * 1000,name = "hottags")
    public Result hottags(){
        int limit=6;
        return  tagService.hots(limit);
    }
    //所有标签
    @GetMapping
    public Result findAll(){
        return  tagService.findAll();
    }
    //所有标签详情
    @GetMapping("detail")
    public Result findAllDetail(){
        return  tagService.findAlldetail();
    }
    //文章详情的标签分类
    @GetMapping("detail/{id}")
    public Result findAlldetailByid(@PathVariable("id")Long id){
        return  tagService.findAlldetailByid(id);
    }

}
