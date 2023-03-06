package com.ldw.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ldw.blog.dao.pojo.Article;
import lombok.Data;

import java.util.List;

@Data//vo主要和前端接口要求的返回数据是保持一致，pojo主要是要和数据库字段保持一致
public class ArticleVo {
    //   显示不出来的在ArticleVo中的id属性上面加上@JsonSerialize(using = ToStringSerializer.class)
//    @JsonSerialize(using = ToStringSerializer.class)  由于加了缓存的原因，需要将id改为string类型，所以用不到它了
    private String id;
    private String title;
    private String summary;
    private Integer commentCounts;
    private Integer viewCounts;
    private Long authorId;
    private Integer weight;
    private String createDate;
    private UserVo author;
//    private String author;
    private ArticleBodyVo body;
    private List<TagVo> tags;
    private List<CategoryVo> categorys;
    private CategoryVo category;
}
