<template>
  <div class="container">
    <el-menu
      router
      :default-active="this.$route.path"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">{{item.navItem}}</el-menu-item>
    </el-menu>
    <div class="login-box" v-show="loginState">
      <el-button type="primary" @click="login" class="button-box">登录</el-button>
      <el-button type="primary" @click="register" class="button-box">注册</el-button>
    </div>
    <div class="login-box" v-show="welcomeState">
      <span>欢迎您：{{user}}</span>
      <el-button type="info" @click="logout" class="button-box">退出登录</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      navList: [
        {
          name: "/index/home",
          navItem: "首页"
        },
        {
          name: "/index/rank",
          navItem: "排行榜"
        },
        {
          name: "/index/bookrack",
          navItem: "我的书架"
        }
      ]
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      if (key == 1) {
        this.$router.push("/index/home");
      } else if (key == 2) {
        this.$router.push("/index/rank");
      } else if (key == 3) {
        this.$router.push("/index/bookrack");
      }
    },
    login() {
      this.$router.push("/login");
    },
    register() {
      this.$router.push("/login");
    },
    logout() {
      this.$store.commit("changeName", "");
      this.$store.commit("changeLoginState", false);
      this.$store.commit("changeUser", {name:'',id:0});
      // this.loginState = !this.$store.state.loginState;
      // this.welcomeState = this.$store.state.loginState;
      // this.user = this.$store.state.userName;
    }
  },
  computed: {
    loginState: function() {
      return !this.$store.state.loginState;
    },
    welcomeState: function() {
      return this.$store.state.loginState;
    },
    user: function() {
      return this.$store.state.userName;
    }
  }
};
</script>

<style lang="less" scoped>
.login-box {
  float: right;
  line-height: 60px;
  .button-box {
    margin: 0 10px;
  }
}
.el-menu-demo {
  display: inline-block;
}
</style>