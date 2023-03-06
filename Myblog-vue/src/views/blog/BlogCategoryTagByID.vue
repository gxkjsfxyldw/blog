<template>
  <div class="me-ct-body" v-title :data-title="title">
      <el-container class="me-ct-container">
        <el-main>
          <div class="me-ct-title me-area">
            <template v-if="this.$route.params.type === 'tag'">
              <img class="me-ct-picture" :src="ct.avatar?ct.avatar:defaultAvatar"/>
              <h3 class="me-ct-name">{{ct.tagName}}</h3>
            </template>

            <template v-else>
              <img class="me-ct-picture" :src="ct.avatar?ct.avatar:defaultAvatar"/>
              <h3 class="me-ct-name">{{ct.categoryName}}</h3>
              <p>{{ct.description}}</p>
            </template>
            <span class="me-ct-meta">{{ct.articles}} 文章</span>
          </div>
<!--        分页 文章列表-->
          <div class="me-ct-articles">
            <article-scroll-page v-bind:query="article"></article-scroll-page>
          </div>
        </el-main>
      </el-container>
  </div>
</template>

<script>
import ArticleScrollPage from "@/components/common/ArticleScrollPage";
import {getTagDetialById} from "@/api/tag";
import {getCategoryDetialById} from "@/api/category";
import defaultAvatar from '@/assets/img/logo.png'

export default {
  name: "BlogCategoryTag",
  components: {
   "article-scroll-page": ArticleScrollPage,
  },
  data(){
      return{
        ct: {},
        article:{
          tagId:null,
          categoryId:null,
        },
        defaultAvatar:defaultAvatar,
      }
  },
  computed:{//网页标签的显示控制
    title(){
      if(this.$route.params.type == 'tag'){
        return "标签分类"
      }else{
        return "文章分类"
      }
    }
  },
  created() {
    //请求后端接口数据
    this.getCategoryOrTagAndArticles();
  },
  methods:{
    getCategoryOrTagAndArticles(){
      var type = this.$route.params.type;
      var id = this.$route.params.id;
      if('tag'== type){
        this.getTagDetialById(id);
        this.article.tagId = id;
      }
      if('category'== type){
        this.getCategoryDetialById(id);
        this.article.categoryId = id;
      }

    },
    getTagDetialById(id){
      //发起http请求，后端的数据
      getTagDetialById(id).then((res)=>{
        if(res.data.success){
          this.ct=res.data.data;
        }else{
          this.$message.error(res.data.msg);
        }
      }).catch((err)=>{
        this.$message.error("系统错误");
      }).finally(()=>{
      })
    },
    getCategoryDetialById(id){
      //发起http请求，后端的数据
      getCategoryDetialById(id).then((res)=>{
        if(res.data.success){
          this.ct=res.data.data;
        }else{
          this.$message.error(res.data.msg);
        }
      }).catch((err)=>{
        this.$message.error("系统错误");
      }).finally(()=>{
      })
    }
  },

}
</script>

<style>
.me-ct-body {
  margin: 60px auto 140px;
  min-width: 100%;
}

.el-main {
  padding: 0;
}

.me-ct-title {
  text-align: center;
  height: 150px;
  padding: 20px;
}

.me-ct-picture {
  width: 60px;
  height: 60px;
}

.me-ct-name {
  font-size: 28px;
}

.me-ct-meta {
  font-size: 12px;
  color: #969696;
}

.me-ct-articles {
  width: 640px;
  margin: 30px auto;
}

</style>
