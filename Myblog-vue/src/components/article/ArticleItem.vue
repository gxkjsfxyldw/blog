<template>
  <el-card class="box-card">
    <!--    文章头部-->
    <div class="me-article-header">
    <!--      获取文章title 通过文章id-->
        <a @click="view(id)" class="me-article-title">{{title}}</a>
        <el-button v-if="weight>0" class="me-article-icon" type="text">置顶</el-button>
        <span class="me-pull-comment me-article-count">
          <i class="me-icon-comment"></i>
          &nbsp;{{commentCounts}}
        </span>
      <span class="me-pull-right me-article-count">
          <i class="el-icon-view"></i>
          &nbsp;{{viewCounts}}
        </span>
    </div>
<!--    描述-->
    <div class="me-article-description">
      {{summary}}
    </div>
<!-- 卡片底部-->
    <div class="me-article-footer">
      <span class="me-article-author">
        <i class="el-icon-author"></i>
        &nbsp;<el-tag>{{author.nickname}}</el-tag>
      </span>

      <el-tag v-for="t in tags":key="t.tagName" size="mini" type="warning">{{t.tagName}}</el-tag>

      <span class="me-pull-right me-article-count">
       	<i class="el-icon-time"></i>
        &nbsp;{{createDate | format}}
      </span>
    </div>
  </el-card>
</template>

<script>

export default {
  name: "ArticleItem",
  //props表示将属性开放，让外部可以进行赋值  后边的表示类型
  props:{
    id:String,
    weight:Number,
    title:String,
    commentCounts:Number,
    viewCounts:Number,
    summary:String,
    author:String,
    tags:Array,
    createDate:String,
  },
  //data只能内部赋值
  data(){
    return{
    }
  },
  methods:{
    view(id){//将id值传到rul上在进行路由跳转
      this.$router.push({path: `/view/${id}`})
    },
  }
}

</script>


<style scoped>

.me-article-header {
  /*padding: 10px 18px;*/
  padding-bottom: 10px;
}

.me-article-title {
  font-weight: 600;
}

.me-article-icon {
  padding: 3px 8px;
}

.me-article-count {
  color: #a6a6a6;
  padding-left: 14px;
  font-size: 13px;
}

.me-pull-right {
  float: right;
}

.me-artile-description {
  font-size: 13px;
  line-height: 24px;
  margin-bottom: 10px;
}

.me-article-author {
  color: #a6a6a6;
  padding-right: 18px;
  font-size: 13px;
}

.el-tag {
  margin-left: 6px;
}

</style>

