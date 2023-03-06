package com.ldw.blog.vo.params;

import com.ldw.blog.dao.pojo.Category;
import com.ldw.blog.vo.CategoryVo;
import com.ldw.blog.vo.TagVo;
import lombok.Data;

import java.util.List;
@Data
public class ArticleParam {
    private Long id;
    private ArticleBodyParam body;
    private CategoryVo category;
    private String summary;
    private List<TagVo> tags;
    private String title;


}
