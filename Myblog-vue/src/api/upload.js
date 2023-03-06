import request from "@/request";

export function upload(formdata){
    return request({//这里就可以直接调用request工具类
        headers: {'Content-Type': 'multipart/form-data'},
        url: '/upload',
        method: 'post',
        data: formdata
    })
}
