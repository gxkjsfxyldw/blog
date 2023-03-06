<template>
  <div v-title :data-title="title">
      <el-container>
        <el-aside class="me-area">
          <ul class="me-month-list">
            <li v-for="a in archives" :key="a.year+a.month" class="me-month-item">
              <el-badge :value="a.count">
                <el-button @click="changeArchive(a.year,a.month)" size="small">
                {{a.year+'年'+a.month+'月'}}
                </el-button>
              </el-badge>
            </li>
          </ul>
        </el-aside>
        <el-main class="me-articles">
          <div class="me-month-title">
          {{currentArchive}}
          </div>
          <article-scroll-page v-bind="article"></article-scroll-page>
        </el-main>
      </el-container>
  </div>
</template>

<script>
import ArticleScrollPage from "@/components/common/ArticleScrollPage";
import {listArchives} from "@/api/article";

export default {
  name: "BlogAchive",
  components: {
   "article-scroll-page": ArticleScrollPage
  },
  data(){
    return{
      article:{
        query:{
          month:this.$route.params.month,
          year:this.$route.params.year,
        }
      },
      archives:[]
    }
  },
  created() {
    this.listArchives()
  },
  watch:{
    '$route'(){
      if(this.$route.params.year && this.$route.params.month){
        this.article.query.year = this.$route.params.year
        this.article.query.month = this.$route.params.month
      }
    }
  },
  computed:{
    title(){
      return this.currentArchive+'-文章归档'
    },
    currentArchive(){
      if(this.article.query.year&&this.article.query.month){
        return `${this.article.query.year}年${this.article.query.month}月`
      }
      return '全部'
    }
  },
  methods:{
    changeArchive(year,month){
      // this.currentArchive = `${year}年${month}月`
      // this.article.query.year = year
      // this.article.query.month = month
      this.$router.push({path: `/archives/${year}/${month}`})
    },
    listArchives(){
      //发起http请求 请求后端的数据
      listArchives().then((res)=>{
        if(res.data.success){
          this.archives = res.data.data;
        }else{
          this.$message({type: 'error', message: '文章归档加载失败!', showClose: true})
        }
      }).catch((err)=>{
        this.$message.error("系统错误");
      }).finally(()=>{
      })
    }
  }
}
</script>

<style scoped>

.el-container {
  width: 640px;
}

.el-aside {
  position: fixed;
  left: 200px;
  margin-right: 50px;
  width: 150px !important;
}

.el-main {
  padding: 0px;
  line-height: 16px;
}

.me-month-list {
  margin-top: 10px;
  margin-bottom: 10px;
  text-align: center;
  list-style-type: none;
}

.me-month-item {
  margin-top: 18px;
  padding: 4px;
  font-size: 18px;
  color: #5FB878;
}

.me-order-list {
  float: right;
}

.me-month-title {
  margin-left: 4px;
  margin-bottom: 12px;
}
</style>
