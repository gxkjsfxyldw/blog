package com.ldw.blog.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ldw.blog.common.aop.LogAnnotation;
import com.ldw.blog.common.cache.Cache;
import com.ldw.blog.service.ArticleService;
import com.ldw.blog.vo.Result;
import com.ldw.blog.vo.params.ArticleParam;
import com.ldw.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//JSON数据进行交互 使用此注解
@RestController
@RequestMapping("/articles")
public class ArticleController {
    //缓存一致问题未解决
    @Autowired
    private ArticleService articleService;

    /**
     * 首页  文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    //打印日志  自己写的注解
    @LogAnnotation(module = "文章",opertion ="获取文章列表" )
//    //统一缓存处理
//    @Cache(expire = 5 * 60 * 1000,name = "listArticle")
    public Result listArticle(@RequestBody(required = false) PageParams pageParams){

        return  articleService.listArticle(pageParams);
    }

    /**
     * 首页最热文章
     * @return
     */

    @PostMapping("hot")
//    //统一缓存处理
//    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public Result hotArticle(){
        int limit=5;
        return  articleService.hotArticle(limit);
    }
    /**
     * 首页最新文章
     * @return
     */
    @PostMapping("new")
//    //统一缓存处理
//    @Cache(expire = 5 * 60 * 1000,name = "newArticles")
    public Result newArticles(){
        int limit=5;
        return  articleService.newArticle(limit);
    }
    /**
     * 文章归档
     * @return
     */
//    //统一缓存处理
//    @Cache(expire = 5 * 60 * 1000,name = "listArchives")
    @PostMapping("listArchives")
    public Result listArchives(){
        return  articleService.listArchives();
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){

        return articleService.findArticleById(articleId);
    }

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }

    /**
     * 修改文章
     * @param articleId
     * @return
     */
    @PostMapping("{id}")
    public Result articleByid(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

}
