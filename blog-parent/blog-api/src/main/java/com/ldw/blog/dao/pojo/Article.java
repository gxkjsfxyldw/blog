package com.ldw.blog.dao.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Article  {
    //实体类的数据类型尽量不用基本数据类型，需要使用包装类型
    //因为mybatis plus会把不是null的字段都会加入sql语句当中
    public static final int Article_Top=1;
    public static final int Article_Common=0;

    private Long id;
    private String title;
    private String summary;
    private Integer commentCounts;
    private Integer viewCounts;
    private Long authorId;
    private Long bodyId;
    private Long categoryId;
    //置顶
    private Integer weight;
    private Long createDate;

}
