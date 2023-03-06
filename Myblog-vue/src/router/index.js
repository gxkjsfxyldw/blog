import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../views/index.vue'
import Home from '@/Home'
import BlogAllCategory from "@/views/blog/BlogAllCategoryTag";
import BlogCategoryTag from "@/views/blog/BlogCategoryTagByID";
import BlogArchive from "@/views/blog/BlogArchive";
import BlogView from "@/views/blog/BlogView";
import store from '@/store'
import {Message} from "element-ui";

Vue.use(VueRouter)

//每个路由对应一个组件
const routes = [
	{ //路由的顺序问题很重要
		path: '/write/:id?',
		name: 'BlogWrite',
		component: ()=>import('@/views/blog/BlogWrite'),
		meta: {
			requireLogin: true
		},
	},
	{
		path: '',
		name: 'Home',
		props:true,
		component: Home,
		redirect:'/index', //当什么都不输入时，重定向到首页
		//  子路由  这些是在登录状态需要显示的路由
		children: [
			{
				path: '/',
				name: 'Index',
				component: ()=>import('@/views/index')
			},
			{
				path: '/:type/all',
				name: 'BlogAllCategory',
				component: ()=>import('@/views/blog/BlogAllCategoryTag')
			},
			{
				path: '/view/:id',
				name: 'BlogView',
				component: ()=>import('@/views/blog/BlogView')
			},
			{
				path: '/:type/:id',
				name: 'BlogCategoryTag',
				component: ()=>import('@/views/blog/BlogCategoryTagByID')
			},
			{
				path: '/archives/:year?/:month?',
				name: 'BlogArchive',
				component: ()=>import('@/views/blog/BlogArchive')
			},
		]
	},
	{
		path: '/Register',
		name: 'Register',
		component: ()=>import('@/views/Register')
	},
	{
		path: '/login',
		name: 'Login',
		component: ()=>import('@/views/Login')
	},
]

// 3.创建router实例。然后传 router配置
const router = new VueRouter({
	mode: 'history',
	routes
})
export default router

//导航守卫
router.beforeEach((to,from,next)=>{
	//如果存在token 说明用户登录过了
	if(store.state.token){
		//路径如果是请求的login登录界面，如果存在token，直接跳回首页，不需要再次登录
		if(top.path=="/login"){
			next({path:'/'})//跳转到首页
		}else{
			//是否有账号信息。如果没有 则获取
			if(store.state.account.length==0){
				//获取用户信息
				store.dispatch("getUserInfo").then((res)=>{
					next();
				}).catch((error)=>{
					Message({
						type:'warning',
						showClose:true,
						message:'登录已经过期'
					})
					next({path:'/'})//跳转到首页
				})
			}else{
				next();
			}
		}
	}else{//未登录时
		if (to.matched.some(r => r.meta.requireLogin)) {
			Message({
				type:'warning',
				showClose:true,
				message:'请先登录'
			})
		}else{
			// next({path:'/login'})//跳转到登录
			next();
		}
	}
})
