//引入映射后端的url
import request from '@/request'
//文章请求接口 test
export function getArticle(query,params){
    return request({//这里就可以直接调用request工具类
        method:'post',
        url:'/articles',//它会自动拼接到baseURL的后面的
        data: {//将查询文章列表的参数返回给后端，后端接受到如果有日期就进行日期的赛选
            page: params.pageNumber,
            pageSize: params.pageSize,
            name: params.name,
            sort: params.sort,
            year: query.year,
            month: query.month,
            tagId: query.tagId,
            categoryId: query.categoryId
        }
    })
}
export function getHotArticle(){
    return request({//这里就可以直接调用request工具类
        method:'post',
        url:'/articles/hot'//它会自动拼接到baseURL的后面的
    })
}

export function getNewArticle(){
    return request({//这里就可以直接调用request工具类
        method:'post',
        url:'/articles/new'//它会自动拼接到baseURL的后面的
    })
}
export function getArchives(){
    return request({//这里就可以直接调用request工具类
        method:'post',
        url:'/articles/listArchives'//它会自动拼接到baseURL的后面的
    })
}
export function findArticleById(id){
    return request({//这里就可以直接调用request工具类
        method:'post',
        url:`/articles/view/${id}`//它会自动拼接到baseURL的后面的
    })
}
export function listArchives(){
    return request({//这里就可以直接调用request工具类
        method:'post',
        url:'/articles/listArchives' //它会自动拼接到baseURL的后面的
    })
}
export function publishArticle(article,token){
    return request({//这里就可以直接调用request工具类
        method:'post',
        url:'/articles/publish' ,//它会自动拼接到baseURL的后面的
        headers:{'Authorization':token},
        data:article
    })
}

export function getArticleById(id){
    return request({//这里就可以直接调用request工具类
        method:'post',
        url:`/articles/${id}` ,//它会自动拼接到baseURL的后面的
    })
}
