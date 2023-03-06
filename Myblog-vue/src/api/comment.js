//引入映射后端的url
import request from '@/request'
//文章请求接口
export function getCommentsByArticle(id){
    return request({//这里就可以直接调用request工具类
        method:'get',
        url:`/comments/article/${id}`//它会自动拼接到baseURL的后面的
    })
}
export function publishComment(comment,token){
    return request({//这里就可以直接调用request工具类
        headers: {'Authorization': token},
        method:'post',
        url:'/comments/create/change',//它会自动拼接到baseURL的后面的
        data:comment
    })
}
