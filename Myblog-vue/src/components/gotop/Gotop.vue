<template>
	<transition>
		<div @click="toTop" v-show="topShow" class="me-to-top">
			<i class="el-icon-caret-top"></i>
		</div>
	</transition>
</template>

<script>
export default {
  name: 'Gotop',
  data(){
    return{
      topShow:false//关闭图标显示
    }
  },
  methods:{
    toTop(){
       document.body.scrollTop=0;//将就浏览器滑动到最顶部
       document.documentElement.scrollTop=0;
       this.topShow=false;//关闭图标显示
    },
    needToTop(){
      let curHeight=document.documentElement.scrollTop||document.body.scrollTop;
      //判断当前的高度
      if(curHeight>400){//当浏览器下拉到400px时才开始出现向上图标
        this.topShow=true;
      }else{
        this.topShow=false;
      }
    }
  },
  //监听器  监听下拉条的高度是否满足需求
  mounted() {
    /**
     *等整个视图都渲染完成
     */
    this.$nextTick(function (){
      window.addEventListener('scroll',this.needToTop);
    });
  }
}
</script>

<style>
.me-to-top {
  background-color: #fff;
  position: fixed;
  right: 100px;
  bottom: 150px;
  width: 40px;
  height: 40px;
  border-radius: 20px;
  cursor: pointer;
  transition: .3s;
  box-shadow: 0 0 6px rgba(0, 0, 0, .12);
  z-index: 5;
}

.me-to-top i {
  color: #5b99e7;
  display: block;
  line-height: 40px;
  text-align: center;
  font-size: 18px;
}
</style>
