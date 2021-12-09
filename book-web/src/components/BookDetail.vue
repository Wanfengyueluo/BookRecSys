<template>
  <el-container>
    <nav-menu></nav-menu>
    <el-header></el-header>
    <el-main>
      <div class="book-item">
        <div class="item-img">
          <img class="avater" :src="book.bookImageUrl" />
        </div>
        <div class="item-info">
          <span class="span-item">书名：{{book.bookTitle}}</span>
          <br />
          <span>作者：{{book.bookAuthor}}</span>
          <br />
          <span>出版年：{{book.publishDate}}</span>
          <br />
          <span>出版社：{{book.press}}</span>
          <div class="item-rate">
            <span>平均评分</span>
            <el-rate
              :max="max"
              :allow-half="allowHalf"
              :disabled="disabled"
              show-score
              score-template="{value}"
              v-model="value"
            ></el-rate>
          </div>
          <div class="item-rate">
            <span>请您评分</span>
            <el-rate
              :max="max"
              show-score
              score-template="{value}"
              v-model="score"
              v-on="change(score)"
            ></el-rate>
          </div>
        </div>
      </div>
      <div class="recommender">
        <BookItem v-show="recommender" :key="index" v-for="(book, index) in userRec" :info="book"></BookItem>
        <BookItem :key="'info-'+index" v-for="(book, index) in bookRec" :info="book"></BookItem>
      </div>
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
      book: {},
      bookRec: [],
      userRec: [],
      score: 0,
      value: null,
      max: 10,
      allowHalf: true,
      disabled: true,
      recommender: false
    };
  },
  methods: {
    async getBookList() {
      //请求书籍信息
      await this.$http
        .get("/api/books/item", {
          params: {
            bookId: this.$route.query.bookId,
            userId: this.$store.state.user.id
          }
        })
        .then(res => {
          console.log(res);
          if (res.data.code == 200) {
            this.book = res.data.data;
            if (res.data.avgScore != 0) {
              this.value = res.data.avgScore;
            }
            if (res.data.myScore != null) {
              this.score = res.data.myScore;
            }
          }
        });
      //请求书籍推荐列表
      await this.$http
        .get("/api/books/bookRecs", {
          params: {
            bookId: this.$route.query.bookId
          }
        })
        .then(res => {
          console.log(res);
          if (res.data.code == 200) {
            this.bookRec = res.data.data;
          }
        });
    },
    change(value) {
      if (this.score > 0) {
        if (this.$store.state.user.id == 0) {
          this.$message.success({
            message: "用户未登录!",
            type: "success",
            center: true
          });
        } else {
          this.$http
            .post(
              "/api/book/rating",
              JSON.stringify({
                userId: this.$store.state.user.id,
                bookId: this.$route.query.bookId,
                score: this.score
              })
            )
            .then(res => {
              if (res.data.code == 200) {
                console.log(res);
                //获取实时用户推荐列表
                this.$http
                  .get("/api/books/streamRecs", {
                    params: {
                      userId: this.$store.state.user.id
                    }
                  })
                  .then(res => {
                    console.log(res);
                    if (res.data.code == 200) {
                      this.userRec = res.data.data;
                      this.recommender = true;
                    }
                  });
              }
            });
        }
      }
    }
  }
};
</script>

<style lang="less" scoped>
.el-container {
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
span {
  font-size: 16px;
  text-align: center;
  margin: 5px 0;
}
.book-item {
  width: 100%;
  height: 300px;
  .item-img {
    width: 200px;
    height: 300px;
    float: left;
  }
  .item-info {
    width: 400px;
    height: 200px;
    text-align: left;
    float: left;
    padding: 50px;
  }
  .item-rate {
    width: 300px;
    float: left;
    margin-top: 20px;
    border-top: 1px solid #ffffff;
  }
}
.avater {
  width: 200px;
  height: 300px;
}
</style>