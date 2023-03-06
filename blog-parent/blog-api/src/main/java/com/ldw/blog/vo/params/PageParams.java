package com.ldw.blog.vo.params;

import lombok.Data;

@Data
public class PageParams {//分页查找返回数据
    private int page=1;//默认从第一页开始
    private int pagesize=10;//默认展示10页

    private Long categoryId;
    private Long tagId;
    private String year;
    private String month;

    public String getMonth(){//如果月份个位数时在其前面加个0  如 01月 02月
        if(this.month!=null&&this.month.length()==1){
            return "0"+this.month;
        }else{
            return this.month;
        }
    }
}
