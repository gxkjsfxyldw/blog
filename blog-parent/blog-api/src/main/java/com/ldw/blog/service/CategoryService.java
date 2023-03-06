package com.ldw.blog.service;
import com.ldw.blog.vo.CategoryVo;
import com.ldw.blog.vo.Result;


public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 写文章
     * @return
     */
    Result findAll();

    /**
     * 文章详情
     * @return
     */
    Result findAlldetail();

    /**
     * 根据文章列表id查找所有对应的文章
     * @param id
     * @return
     */
    Result findAlldetailById(Long id);
}
