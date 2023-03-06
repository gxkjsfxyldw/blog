<!--定义顶部导航栏-->
<template >
    <el-header class="me-area">
      <el-row class="me-header" >
<!--    logo 区域-->
        <el-col :span="2" class="me-header-left" style="margin-left: 65px">
          <router-link to="/"class="me-title">
            <img src="@/assets/img/logo.jpg"/>
          </router-link>
        </el-col>
<!--              if  -->
        <el-col v-if="!simple":span="16">
          <el-menu
              :router=true
              menu-trigger="click"
              active-text-color="#00aaff"
              :default-active="activeIndex"
              mode="horizontal">
            <el-menu-item index="/">首页</el-menu-item>
            <el-menu-item index="/category/all">文章分类</el-menu-item>
            <el-menu-item index="/tag/all">标签</el-menu-item>
            <el-menu-item index="/archives">文章归档</el-menu-item>
<!--            搜索文章-->
            <el-menu-item >
              <el-input size="mini" style="margin-top:-8px;margin-left: 100px;width: 60% " v-model="input" placeholder="搜索文章"></el-input>
              <el-button size="small" style="margin-top: -6px;margin-left: 2px;width: 15%;height: 50% " class="el-icon-search"></el-button>
            </el-menu-item>
			
            <el-col :span="3" :offset="4" style="margin-left: 110px">
              <el-menu-item index="/write">
                <i class="el-icon-edit"></i>
                写文章
              </el-menu-item>
            </el-col>
          </el-menu>
        </el-col>
<!--              else  -->
        <template v-else>
          <slot></slot>
        </template>

        <el-col :span="4">
          <el-menu
              :router=true
              menu-trigger="click"
              mode="horizontal"
              active-text-color="#00aaff">
<!--              if  -->
            <template v-if="!user.login">
              <el-menu-item index="/login">
                <el-button type="text">登录</el-button>
              </el-menu-item>
              <el-menu-item index="/register">
                <el-button type="text">注册</el-button>
              </el-menu-item>
            </template>
<!--              else  -->
            <template v-else>
              <el-submenu index>
                <template slot="title">
                  <img class="me-header-picture" :src="user.avatar"/>
                </template>
                <el-menu-item index @click="logout"><i class="el-icon-back"></i>退出</el-menu-item>
              </el-submenu>
            </template>
          </el-menu>
        </el-col>
      </el-row>
    </el-header>

</template>

<script>
import {Message} from "element-ui";

export default {
  name: "BaseHeader",
  props:{
    activeIndex:String,
    simple:{
      type:Boolean,
      default:false
    }
  },
  data(){
    return{
    }
  },
  computed:{
    //获取到登录用户 显示已登录
    user(){
      let login=this.$store.state.account.length!=0;
      let avatar=this.$store.state.avatar;
      return{
        login,
        avatar,
      }
    }
  },
  methods:{
    logout(){
          this.$store.dispatch("logout").then((res)=>{
            this.$router.push({path:'/'})
          }).catch((error)=>{
            if(error!=='error'){
              this.$message({message:error,type:'error',showClose:true})
            }
          })
      }
  }

}
</script>

<style>
.el-button{
  color: #00aaff;
}
.el-header {
  position: fixed;
  z-index: 1024;
  min-width: 100%;
  box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
}
.me-title {
  margin-top: 10px;
  font-size: 24px;
}
.me-header-left {
  margin-top: 10px;
}
.me-title img {
  max-height: 2.4rem;
  max-width: 100%;
}

.me-header-picture {
  width: 36px;
  height: 36px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
  background-color: #00aaff;
}
</style>
