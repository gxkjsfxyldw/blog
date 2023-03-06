export function formatTime(time){
    const d=new Date(time)//创建文章的时间
    const now=Date.now()//当前系统时间

    const diff=(now-d)/1000

    if(diff<30){
        return'刚刚'
    }else if(diff<3600){//less 1 hour
        return Math.ceil(diff/60)+'分钟前'
    }else if(diff<3600*24){
        return Math.ceil(diff/3600)+'小时前'
    }else if(diff<3600*24*2){
        return '1天前'
    }
return time
}
//工具类都要到main.js中进行注册
