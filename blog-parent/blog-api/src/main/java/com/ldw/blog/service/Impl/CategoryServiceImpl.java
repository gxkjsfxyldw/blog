package com.ldw.blog.service.Impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.ldw.blog.dao.mapper.CategoryMapper;
import com.ldw.blog.dao.pojo.Category;
import com.ldw.blog.service.CategoryService;
import com.ldw.blog.vo.CategoryVo;
import com.ldw.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        //缓存新改 用pojo里面的转换为string再存储
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }
    //写文章
    @Override
    public Result findAll() {
        LambdaQueryWrapper<Category>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        //页面交互的对象，
        return Result.success(copyList(categories));
    }

    @Override
    public Result findAlldetail() {
        LambdaQueryWrapper<Category>queryWrapper=new LambdaQueryWrapper<>();
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        //页面交互的对象，
        return Result.success(copyList(categories));
    }

    @Override
    public Result findAlldetailById(Long id) {
        Category category=categoryMapper.selectById(id);
        return Result.success(copy(category));
    }

    public CategoryVo copy(Category category){
        CategoryVo categoryVo=new CategoryVo();
        //缓存新改 用pojo里面的转换为string再存储
        categoryVo.setId(String.valueOf(category.getId()));
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    public List<CategoryVo> copyList(List<Category>categories){
        List<CategoryVo> categoryVos=new ArrayList<>();
        for (Category category : categories) {
            categoryVos.add(copy(category));
        }
        return categoryVos;
    }
}
