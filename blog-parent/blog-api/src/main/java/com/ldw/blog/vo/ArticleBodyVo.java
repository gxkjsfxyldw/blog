package com.ldw.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ArticleBodyVo {
    //   显示不出来的在ArticleVo中的id属性上面加上@JsonSerialize(using = ToStringSerializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;

}
