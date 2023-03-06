<template>
  <div id="app">
	  <!-- 路由出口，路由匹配的组件将在这里渲染 -->
	  <router-view>

    </router-view>
	  <!-- 制作回到顶部的组件 -->
	  <go-top></go-top>
  </div>
</template>

<script>
	//导入组件
import Gotop from "@/components/gotop/Gotop"

export default {
  name: 'app',
  components: {
	  //注册组件并起一个别名 go-top
	"go-top":Gotop
  }
}
</script>


<style>
* { margin: 0; padding: 0; }

body {
  background-color: #ffffff;
  font-weight: 400;
  font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  line-height: 1.7;
}
a {
  cursor: pointer;
  text-decoration: none;
  /* 动画效果，css的属性值在一定的时间内从一个状态平滑的过渡到另一个状态 ease-out　先快，后慢 */
  transition: none 86ms ease-out;
}
a:hover {
  color: #2082ff;
}

html{height:100%;}
body{min-height:100%;}
/*生成相对定位的元素，相对于其正常位置进行定位*/
body{position:relative;}

</style>

