package com.ldw.blog.controller;

import com.ldw.blog.service.CategoryService;
import com.ldw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result categoryes(){
        return categoryService.findAll();
    }

    //文章分类标签
    @GetMapping("detail")
    public Result categoryesDetail(){
        return categoryService.findAlldetail();
    }

    //分类文章列表
    @GetMapping("detail/{id}")
    public Result categoryesDetailById(@PathVariable("id") Long id){
        return categoryService.findAlldetailById(id);
    }


}
