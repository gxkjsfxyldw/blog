<template>
  <el-card>
    <h1 class="me-author-name">Mr_Li个人博客</h1>
    <div class="me-author-description">
      <span><i class="el-icon-location-outline"></i>&nbsp;广西来宾&nbsp;&nbsp;&nbsp;&nbsp;</span>
      <span><i class="iconfont icon-Steve-Jobs"></i>&nbsp;Java工程师</span>
    </div>
    <div class="me-author-tool">
      <i @click="showTool(qq)" :title="qq.title" class="iconfont icon-QQ-circle-fill"></i>
      <i @click="showTool(bilibili)" :title="bilibili.title" class="iconfont icon-github"></i>
    </div>
  </el-card>



</template>

<script>
export default {
  name: "CardMe",
  data(){
      return{
        qq:{title:'QQ',message:'2632711107'},
        bilibili:{title: '哔哩哔哩',message:''}
      }
  },
  methods:{
    showTool(tool){
      this.$message({
        duration:0,
        showClose:true,
        dangerouslyUseHTMLString:true,
        message: '<strong>' + tool.message + '</strong>'
      });
    }
  }




}
</script>

<style scoped>
.me-author-name {
  text-align: center;
  font-size: 30px;
  border-bottom: 1px solid #00aaff;
}

.me-author-description {
  padding: 8px 0;
  margin-left: 20px;
  margin-top: 10px;
}

.me-icon-job {
  padding-left: 16px;
}

.me-author-tool {
  text-align: center;
  padding-top: 5px;
}

.me-author-tool i {
  cursor: pointer;
  padding: 4px 10px;
  font-size: 30px;
}

</style>
