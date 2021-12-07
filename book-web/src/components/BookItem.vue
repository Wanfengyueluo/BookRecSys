<template>
  <div class="product">
    <router-link class="product-main" :to="{ path: '/index/books/item', query: { bookId: info.bookId }}">
      <img class="avater" :src="info.bookImageUrl" />
      <div class="product-add-cart" @click.prevent="handleCart">收藏</div>
    </router-link>
  </div>
</template>
<script>
export default {
  props: {
    info: Object
  },
  data() {
    return {
      errorBooksImg: "this.src'=@/assets/logo.png'"
    };
  },
  methods: {
    handleCart() {
      this.$http
        .post(
          "/api/book/favorite",
          JSON.stringify({
            userId: this.$store.state.user.id,
            bookId: this.info.bookId
          })
        )
        .then(res => {
          if (res.data.code == 200) {
            this.$message.success({
              message: "书籍收藏成功!",
              type: "success",
              center: true
            });
          }
        });
    }
  }
};
</script>
<style lang="less" scoped>
.product {
  width: 20%;
  float: left;
  height: 350px;
  margin-bottom: 10px;
}
.product-main {
  display: block;
  margin: 16px;
  padding: 16px;
  border: 1px solid #dddee1;
  border-radius: 6px;
  overflow: hidden;
  background: #fff;
  text-align: center;
  text-decoration: none;
  position: relative;
}
.product-main img {
  width: 100%;
  height: 300px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.product-main:hover h4 {
  color: #0070c9;
}
.avater {
  background-image: url("../assets/loaderror.jpg");
  background-size: 100% 250px;
  background-repeat: no-repeat;
}
.product-color {
  display: block;
  width: 16px;
  height: 16px;
  border: 1px solid #dddee1;
  border-radius: 50%;
  margin: 6px auto;
}

.product-add-cart {
  display: none;
  padding: 4px 8px;
  background: #2d8cf0;
  color: #fff;
  font-size: 12px;
  border-radius: 3px;
  cursor: pointer;
  position: absolute;
  top: 5px;
  right: 5px;
}
.product-main:hover .product-add-cart {
  display: inline-block;
}
</style>