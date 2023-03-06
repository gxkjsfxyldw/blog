package com.ldw.blog.vo.params;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CommentParam {
    //   显示不出来的在ArticleVo中的id属性上面加上@JsonSerialize(using = ToStringSerializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;
    private String content;
    private Long parent;
    private Long toUserId;
}
