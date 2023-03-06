import Vue from 'vue'
import App from './App.vue'
//引入路由配置
import router from './router'
//引入element Ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI)
//引入时间格式化
import {formatTime} from "@/utils/time";
//引入 ajax
import axios from 'axios'
import VueAxios from "vue-axios";
//引入store
import store from './store'
import lodash from 'lodash'

//引入iconfont.css图标样式
import '@/assets/icon/iconfont.css'

Vue.use(VueAxios,axios)
Object.defineProperty(Vue.prototype, '$_', { value: lodash })

//自定义指令 title
Vue.directive('title',function (el,binding){
  document.title=el.dataset.title
})
//格式化时间
Vue.filter('format',formatTime)

Vue.config.productionTip = false
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
