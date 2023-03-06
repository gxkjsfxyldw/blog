package com.ldw.blog.vo;
import lombok.Data;
import java.util.List;

@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;

}
