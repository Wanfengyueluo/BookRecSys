<template>
  <el-container>
    <nav-menu></nav-menu>
    <el-header></el-header>
    <el-main>
      <div class="guess" v-show="guessIsdisplay">
          <div>
              <span>猜你喜欢</span>
          </div>
        <BookItem :key="index" v-for="(book, index) in likes" :info="book"></BookItem>
        <div style="clear:both"></div>
      </div>
      <div class="hot-book">
          <div>
              <span>最热书籍</span>
          </div>
          <BookItem :key="index" v-for="(book, index) in hots" :info="book"></BookItem>
          <div style="clear:both"></div>
      </div>
      <div class="high-book">
          <div>
              <span>高分书籍</span>
          </div>
          <BookItem :key="index" v-for="(book, index) in highs" :info="book"></BookItem>
          <div style="clear:both"></div>
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
      likes: [],
      hots: [],
      highs: [],
      guessIsdisplay:false
    };
  },
  methods: {
    async getBookList() {
        await this.$http.get("/api/likeBooks",{
          params:{
            "userId":this.$store.state.user.id
          }
      }).then(res => {
          if(res.data.code==200){
              this.guessIsdisplay=true;
              this.likes = res.data.data
          }
      });
      await this.$http.get("/api/hotBooks").then(res => {
        this.hots = res.data.data
      });
      await this.$http.get("/api/highBooks").then(res => {
        this.highs = res.data.data
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
span{
    font-size: 20px;
}

</style>