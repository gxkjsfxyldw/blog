package com.ldw.blog.dao.dos;

import lombok.Data;

@Data //这里定义的字段是数据库里面没有的
public class Archives {

    private Integer year;
    private Integer month;
    private Long count;

}
