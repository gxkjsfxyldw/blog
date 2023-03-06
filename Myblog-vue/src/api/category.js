//分类请求接口
import request from "@/request";


export function listCategorysDetial(){
    return request({//这里就可以直接调用request工具类
        method:'get',
        url:'/categorys/detail'//它会自动拼接到baseURL的后面的
    })
}
export function getCategoryDetialById(id){
    return request({//这里就可以直接调用request工具类
        method:'get',
        url:`/categorys/detail/${id}`//它会自动拼接到baseURL的后面的
    })
}
export function getAllcategory(){
    return request({//这里就可以直接调用request工具类
        method:'get',
        url:'/categorys'//它会自动拼接到baseURL的后面的
    })
}
