<template>
  <div id="home">
    <el-container>

      <base-header :activeIndex="activeIndex"></base-header>

      <router-view class="me-container"/>

      <base-footer v-show="footerShow"></base-footer>

    </el-container>

  </div>
</template>

<script>
  import BaseHeader from "@/components/common/BaseHeader";
  import BaseFooter from "@/components/common/BaseFooter";

export default {
  name: "Home",
  data(){
      return{
        activeIndex:'/',
        footerShow:true
      }
  },
  //给导入的组件起别名,并注册
  components:{
    'base-header':BaseHeader,
    'base-footer':BaseFooter,
  },
  beforeRouteEnter(to,from ,next){
    //当鼠标点击不同的导航栏时，进行切换不同的路由
    next(vm=>{
      vm.activeIndex=to.path
    })
  },
  beforeRouteUpdate(to,from,next){
    //当处在 首页时显示页脚
    if(to.path=='/'){
      this.footerShow=true
    }else{
      this.footerShow=false
    }
    this.activeIndex=to.path
    next()
  }

}
</script>

<style scoped>
.me-container{
  margin: 100px auto 140px;
}
</style>
