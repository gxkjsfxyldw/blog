package com.ldw.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Permission {
    //id主键自增
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String path;

    private String description;

}
