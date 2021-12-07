<template>
  <el-container>
    <nav-menu></nav-menu>
    <el-header></el-header>
    <el-main>
      <BookItem :key="index" v-for="(book, index) in books" :info="book"></BookItem>
    </el-main>
  </el-container>
</template>

<script>
import NavMenu from "../components/NavMenu.vue";
import BookItem from "../components/BookItem.vue";
export default {
  components: {
    NavMenu,
    BookItem
  },
  created() {
    this.getBookList();
  },
  data() {
    return {
      books: []
    };
  },
  methods: {
    async getBookList() {
      await this.$http
        .get("/api/book/favorite", {
          params: {
            userId: this.$store.state.user.id
          }
        })
        .then(res => {
          if (res.data.code == 200) {
            this.books = res.data.data;
          }
        });
    }
  }
};
</script>

<style lang="less" scoped>
.el-container {
  //   height: 2000px;
  margin: 0;
  padding: 0;
}
.el-header {
  height: 0 !important;
}
.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  margin-top: 2px;
  overflow: hidden;
  padding: 40px;
  // height: 1600px;
}
</style>