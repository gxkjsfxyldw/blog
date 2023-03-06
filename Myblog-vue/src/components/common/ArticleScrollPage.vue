<template>

  <scroll-page
      :loading="loading"
      :offset="offset"
      :no-data="noData"
      v-on:load="load">
<!--@load="load 不能使用静态数据测试-->
    <Article-item
        v-for="article in articles"
        :key="article.id"
        v-bind="article">
    </Article-item>
  </scroll-page>
</template>


<script>
import ScrollPage from "@/components/downpage"
import ArticleItem from "@/components/article/ArticleItem";
import {getArticle} from "@/api/article";

export default {
  name: "ArticleScrollPage",
  components:{
    "scroll-page":ScrollPage,
    "Article-item":ArticleItem
  },
  props:{
    query:{
      type: Object,
      default(){
        return {}
      }
    },
    offset: {
      type: Number,
      default: 100
    },
    page: {
      type: Object,
      default() {
        return {}
      }
    },
  },
  data(){
    return{
      loading : false,//是否显示加载
      noData : false,
      articles:[],
      innerPage:{
        pageNumber: 1,
        page: 1,
        pageSize:10,
        tagId:null,
        categoryId:null
      },
    }
  },
  watch: {
    'query': {
      handler() {
        this.noData = false
        this.articles = []
        this.innerPage.pageNumber = 1
        this.getArticle()
      },
      deep: true
    },
    'page': {
      handler() {
        this.noData = false
        this.articles = []
        this.innerPage = this.page
        this.getArticle()
      },
      deep: true
    }
  },
  created() {
    this.getArticle();
  },
  methods:{
    view(id) {
      this.$router.push({path: `/view/${id}`})
    },
    load(){
      //如果触发分页，需要调用接口，加载文章
      // alert("触发分页");
      this.getArticle();
    },
    getArticle(){
      let that = this
      //获取后端接口请求文章列表数据
      that.loading = true
      // this.axios.post("http://localhost:8888/articles",this.innerPage).then((res)=>{
      //使用工具类封装后不用上面的语句发送请求了
        getArticle(that.query, that.innerPage).then(res => {
          let newArticles = res.data.data
          if (newArticles && newArticles.length > 0) {
            that.innerPage.pageNumber += 1
            that.articles = that.articles.concat(newArticles)
          } else {
            that.noData = true
          }
      }).catch((error)=>{
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章加载失败!', showClose: true})
          }
      }).finally(()=>{
          that.loading=false;//数据加载完成后关闭加载
      })
    }
  },

}
</script>

<style scoped>
.el-card {
  border-radius: 0;
}

.el-card:not(:first-child) {
  margin-top: 10px;
}
</style>
