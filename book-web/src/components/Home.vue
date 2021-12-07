<template>
  <el-container>
    <nav-menu></nav-menu>
    <el-header>
      <div class="header-search">
        <el-input placeholder="书名、作者" v-model="searchKey">
          <el-button @click="searchBook" slot="append" icon="el-icon-search"></el-button>
        </el-input>
      </div>
    </el-header>
    <el-main>
      <BookItem :key="index" v-for="(book, index) in bookLists" :info="book"></BookItem>
    </el-main>
    <div class="page">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        layout="prev, pager, next"
        :total="total"
      ></el-pagination>
    </div>
  </el-container>
</template>

<script>
import BookItem from "../components/BookItem.vue";
import NavMenu from "../components/NavMenu.vue";
export default {
  components: {
    BookItem,
    NavMenu
  },
  created() {
    this.getBookList();
  },
  data() {
    return {
      searchKey: "",
      bookLists: [],
      total: 0,
      currentPage: 1,
      pageSize: 20,
      state: 0 //为0表示第一次请求，查询全部数据返回total
    };
  },
  methods: {
    async getBookList() {
      var params = {
        state: this.state,
        page: this.currentPage,
        size: this.pageSize
      };
      // console.log("before:"+this.state)
      const result = await this.$http
        .post("/api/books", JSON.stringify(params))
        .then(res => {
          this.bookLists = res.data.data;
          this.state = res.data.state;
          this.total = res.data.total;
        });
    },
    async searchBook() {
      this.$message("查询书籍...");
      this.state = 0;
      if (this.searchKey == "") {
        this.getBookList();
      } else {
        await this.$http
          .get("/api/es/queryBook", {
            params: {
              keyword: this.searchKey,
              state: this.state,
              page: this.currentPage,
              size: this.pageSize
            }
          })
          .then(res => {
            if (res.data.code == 200) {
              console.log(res.data.data);
              this.bookLists = res.data.data;
              this.state = res.data.state;
              this.total = res.data.total;
            }
          });
      }
    },
    async handleCurrentChange(val) {
      this.currentPage = val;
      var params = {
        state: this.state,
        page: this.currentPage,
        size: this.pageSize
      };
      const result = await this.$http
        .post("/api/books", JSON.stringify(params))
        .then(res => {
          this.bookLists = res.data.data;
        });
      this.window.scroll(0, 100);
    }
  },
  computed: {}
};
</script>

<style lang="less" scoped>
.el-container {
  height: 2000px;
  margin: 0;
  padding: 0;
  .page {
    margin-top: 20px;
    padding: 0;
    width: 25%;
    height: 300px;
    position: relative;
    left: 50%;
    transform: translate(-50%);
  }
}
.el-header {
  height: 100px !important;
  text-align: center;
  line-height: 60px;
  // box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  margin: 0;
  padding: 0;
  border-radius: 5px;
  background-color: #e9eef3;
  .header-search {
    text-align: center;
    height: 60px;
    width: 50%;
    position: relative;
    top: 20px;
    left: 25%;
    padding: 5px 25px;
  }
}
.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  margin-top: 2px;
  overflow: hidden;
  padding: 40px;
  height: 1600px;
}
</style>