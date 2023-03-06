import request from "@/request";

export function register(user){
    return request({//这里就可以直接调用request工具类
        method:'post',
        url:`/register`,//它会自动拼接到baseURL的后面的
        data:user //这个是要传回后端的的注册用户
    })
}
export function getUserInfo(){
    return request({//这里就可以直接调用request工具类
        method:'get',
        url:`/users/currentUser`,//它会自动拼接到baseURL的后面的
    })
}
export function logout(){
    return request({//这里就可以直接调用request工具类
        method:'get',
        url:'/logout',//它会自动拼接到baseURL的后面的
    })
}
export function login(data){
    return request({//这里就可以直接调用request工具类
        method:'post',
        url:'/login',//它会自动拼接到baseURL的后面的
        data
    })
}
