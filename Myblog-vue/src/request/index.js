import axios from "axios";
const service=axios.create({
    //将端口进行映射，这样在其他地方只需要引用就可以了  服务器地址 http://101.34.23.203:9090
	 baseURL:"http://localhost:8888",
     // baseURL:"http://101.34.23.203:9090",
    timeout:10000
})
//将此方法声明为公开，让其他组件可以访问到
export default service;

//拦截器
import store from '@/store'
import {Message} from "element-ui";

//添加请求拦截器
service.interceptors.request.use(function (config){
//    在发送请求前做些什么
    if(store.state.token){//判断token是否存在
        //添加一个请求头为token，只要是登录就有，根据请求头就可以获取token,发起任何请求都会添加token
        config.headers['Authorization']=localStorage.token
    }
    return config;
},function (error){
//    对请求错误做点什么
    return Promise.reject(error)
});
//添加相应拦截器
service.interceptors.response.use(function (response){
//    对响应数据做点什么
    const res=response.data;
//    0为成功状态
    if(res!==200){
    //  未登录
        if(res.code===90002){
            Message({
                type:'warning',
                showClose:true,
                message:'未登录或登录超时，请重新登录'
            })
            return Promise.reject('error');
        }
    }
    return response;
})
