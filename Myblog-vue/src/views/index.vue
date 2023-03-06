<template>
  <div>
    <el-container>
      <el-main  class="me-articles">
<!--        使用组件-->
        <el-main class="me-articles">
          <ArticleScrollPage>
<!--            下拉加载-->
          </ArticleScrollPage>
        </el-main>
      </el-main>
      <el-aside >
        <CardMe></CardMe>
        <card-tag :tags="hotTags"></card-tag>
        <card-article cardHeader="最热文章" :articles="hotArticles"></card-article>
        <card-archive cardHeader="文章归档" :archives="archives"></card-archive>
        <card-article cardHeader="最新文章" :articles="newArticles"></card-article>
      </el-aside>
    </el-container>
  </div>

</template>

<script>
//引入组件
import ArticleScrollPage from "@/components/common/ArticleScrollPage.vue";
import CardMe from "@/components/card/CardMe";
import CarTag from "@/components/card/CarTag";
import CardArticle from "@/components/card/CardArticle";
import CardArchive from "@/components/card/CardArchive";
import {getHotTags} from "@/api/tag";
import {getHotArticle,getNewArticle,getArchives} from "@/api/article";


export default {
  name: "index",
  //注册组件
  components:{
    ArticleScrollPage,
    CardMe,
    "card-tag":CarTag,
    "card-article":CardArticle,
    "card-archive":CardArchive,
  },
  data() {
    return {
      hotTags:[],
      hotArticles:[],
      newArticles:[],
      archives:[],
    }
  },
  created() { //进入首页即开始调用
    this.getHotTags();
    this.getHotArticle();
    this.getNewArticle();
    this.getArchives();
  },
  methods:{
    getHotTags(){
      //发起http请求，后端的数据
      getHotTags().then((res)=>{
         if(res.data.success){
           this.hotTags=res.data.data;
         }else{
           this.$message.error(res.data.msg);
         }
      }).catch((err)=>{
        this.$message.error("系统错误");
      }).finally(()=>{
      })
    },
    getHotArticle(){
      //发起http请求，后端的数据
      getHotArticle().then((res)=>{
        if(res.data.success){
          this.hotArticles=res.data.data;
        }else{
          this.$message.error(res.data.msg);
        }
      }).catch((err)=>{
        this.$message.error("系统错误");
      }).finally(()=>{
      })
    },
    getNewArticle(){
      //发起http请求，后端的数据
      getNewArticle().then((res)=>{
        if(res.data.success){
          console.log(res.data)
          this.newArticles=res.data.data;
        }else{
          this.$message.error(res.data.msg);
        }
      }).catch((err)=>{
        this.$message.error("系统错误");
      }).finally(()=>{
      })
    },
    getArchives(){
      //发起http请求，后端的数据
      getArchives().then((res)=>{
        if(res.data.success){
          console.log(res.data)
          this.archives=res.data.data;
        }else{
          this.$message.error(res.data.msg);
        }
      }).catch((err)=>{
        this.$message.error("系统错误");
      }).finally(()=>{
      })
    },
  }
}
</script>

<style scoped>

.el-container {
  width: 960px;
}

.el-aside {
  margin-left: 20px;
  width: 260px;
}

.el-main {
  padding: 0px;
  line-height: 16px;
}

.el-card {
  border-radius: 0;
}

.el-card:not(:first-child) {
  margin-top: 20px;
}
</style>
