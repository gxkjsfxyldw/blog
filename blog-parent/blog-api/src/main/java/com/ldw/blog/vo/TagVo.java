package com.ldw.blog.vo;

import lombok.Data;

@Data //vo主要和前端接口要求的返回数据是保持一致，pojo主要是要和数据库字段保持一致
public class TagVo {
    private String id;
    private String tagName;
    private String avatar;
}
