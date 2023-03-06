//引入映射后端的url
import request from '@/request'
//文章请求接口
export function getHotTags(){
    return request({//这里就可以直接调用request工具类
        method:'get',
        url:'/tags/hot'//它会自动拼接到baseURL的后面的
    })
}
//标签请求接口
export function listTagsDetial(){
    return request({//这里就可以直接调用request工具类
        method:'get',
        url:'/tags/detail'//它会自动拼接到baseURL的后面的
    })
}
//标签详情请求接口
export function getTagDetialById(id){
    return request({//这里就可以直接调用request工具类
        method:'get',
        url:`/tags/detail/${id}`//它会自动拼接到baseURL的后面的
    })
}
//所有标签
export function getAlltags(){
    return request({//这里就可以直接调用request工具类
        method:'get',
        url:'/tags'//它会自动拼接到baseURL的后面的
    })
}
