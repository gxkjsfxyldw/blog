import Vue from "vue";
import Vuex from 'vuex'
import {register,getUserInfo,logout,login} from '@/api/register'

Vue.use(Vuex)
export default new Vuex.Store({
    //需要记录的状态信息
    state:{
        id:'',
        account:'',
        name:'',
        avatar:'',
        token:localStorage.token,
    },
    mutations:{
        SET_TOKEN:(state,token)=>{
            state.token=token;
        },
        SET_ACCOUNT:(state,account)=>{
            state.account=account;
        },
        SET_NAME:(state,name)=>{
            state.name=name;
        },
        SET_AVATAR:(state,avatar)=>{
            state.avatar=avatar;
        },
        SET_ID:(state,id)=>{
            state.id=id;
        },
    },
    actions:{
        register({commit},user){//这个表示自带参数commit，外加传入一个user对象参数
            //Promise对象代表了未来将要发生的事件，用来传递早步操作的消息。
            //rejected：意味着操作失败。
            return new Promise((resolve,reject)=>{
                //异步处理
                //处理结束后调resolve 或 reject
                // 当异步代码执行成功时，我们才会调resolve（..…），当异步代码关败时就会调用reject(...)
                register(user).then((res)=>{//请求后端接口
                    //user就是后端的登录用户
                    //判断是否注册成功
                    if(res.data.success){
                        //获取到后端的token
                        commit('SET_TOKEN',res.data.data)
                        //将后端传的token存储到本地
                        localStorage.token=res.data.data
                        resolve()
                    }else{
                        reject(res.data.msg)
                    }
                }).catch((error)=>{
                    reject(error)
                })
            })
        },
        getUserInfo({commit}){//这个表示自带参数commit，
            //Promise对象代表了未来将要发生的事件，用来传递早步操作的消息。
            //rejected：意味着操作失败。
            return new Promise((resolve,reject)=>{
                //异步处理
                //处理结束后调resolve 或 reject
                // 当异步代码执行成功时，我们才会调resolve（..…），当异步代码关败时就会调用reject(...)
                getUserInfo().then((res)=>{//请求后端接口
                    //user就是后端的登录用户
                    //判断是否注册成功
                    if(res.data.success){
                        //将后端获取到的用户信息进行赋值
                        commit('SET_ID',res.data.data.id);
                        commit('SET_ACCOUNT',res.data.data.account);
                        commit('SET_NAME',res.data.data.name);
                        commit('SET_AVATAR',res.data.data.avatar);
                        resolve(res.data)//下一步的操作需要用户信息
                    }else{
                        //获取失败就将值赋值为空
                        commit('SET_TOKEN','');
                        commit('SET_ID','');
                        commit('SET_ACCOUNT','');
                        commit('SET_NAME','');
                        commit('SET_AVATAR','');
                        localStorage.removeItem("token") //将token清除
                        reject(res.data.msg)
                    }
                }).catch((error)=>{
                    //获取失败就将值赋值为空
                    commit('SET_TOKEN','');
                    commit('SET_ID','');
                    commit('SET_ACCOUNT','');
                    commit('SET_NAME','');
                    commit('SET_AVATAR','');
                    localStorage.removeItem("token") //将token清除
                    reject(error)
                })
            })
        },
        //登录
        login({commit},user){//这个表示自带参数commit
            //Promise对象代表了未来将要发生的事件，用来传递早步操作的消息。
            //rejected：意味着操作失败。
            return new Promise((resolve,reject)=>{
                //异步处理
                //处理结束后调resolve 或 reject
                // 当异步代码执行成功时，我们才会调resolve（..…），当异步代码关败时就会调用reject(...)
                login(user).then((res)=>{//请求后端接口
                    //user就是后端的登录用户
                    //判断是否登录成功
                    if(res.data.success){
                        //获取到后端的token
                        commit('SET_TOKEN',res.data.data)
                        //将后端传的token存储到本地
                        localStorage.token=res.data.data;
                        resolve()
                    }else{
                        reject(res.data.msg)
                    }
                }).catch((error)=>{
                    reject(error)
                })
            })
        },
        //退出登录
        logout({commit}){//这个表示自带参数commit
            //Promise对象代表了未来将要发生的事件，用来传递早步操作的消息。
            //rejected：意味着操作失败。
            return new Promise((resolve,reject)=>{
                //异步处理
                //处理结束后调resolve 或 reject
                // 当异步代码执行成功时，我们才会调resolve（..…），当异步代码关败时就会调用reject(...)
                logout().then((res)=>{//请求后端接口
                    //user就是后端的登录用户
                    //判断是否注册成功
                    if(res.data.success){
                        //获取失败就将值赋值为空
                        commit('SET_TOKEN','');
                        commit('SET_ID','');
                        commit('SET_ACCOUNT','');
                        commit('SET_NAME','');
                        commit('SET_AVATAR','');
                        localStorage.removeItem("token") //将token清除
                        resolve()
                    }else{
                        reject(res.data.msg)
                    }
                }).catch((error)=>{
                    reject(error)
                })
            })
        },
    }
})
