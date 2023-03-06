<template>
  <el-card :body-style="{padding:'8px 18px'}">
    <div slot="header" class="me-category-header">
      <span>{{cardHeader}}</span>
    </div>
    <ul class="me-category-list">
      <li class="me-category-item" v-for="a in articles" :key="a.id" @click="view(a.id)" :style="itemStyle">
        <a> {{a.title}}</a>
      </li>
    </ul>
  </el-card>

</template>

<script>
export default {
  name: "CardArticle",
  props:{
    cardHeader :String,
    articles: Array,
    itemStyle:Object
  },
  methods:{
    view(id){
        this.$router.push({path:`/view/${id}`});
    }
  }

}
</script>

<style scoped>
.me-category-header {
  font-weight: 600;
}

.me-category-list {
  list-style-type: none;
}

.me-category-item {
  padding: 4px;
  font-size: 14px;
  color: #00aaff;
}

.me-category-item a:hover {
  text-decoration: underline;
}
</style>
