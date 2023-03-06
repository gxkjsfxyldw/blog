package com.ldw.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import java.util.List;

@Data
public class CommentVo {
//防止前端 精度损失 把id转为string
// 分布式id 比较长，传到前端 会有精度损失，必须转为string类型 进行传输，就不会有问题了
//    @JsonSerialize(using = ToStringSerializer.class)//注意导包不要倒错了
    private String id;

    private String content;
    private Long createDate;
    private UserVo author;
    private List<CommentVo> childrens;
    private Integer level;
    private UserVo toUser;
}
