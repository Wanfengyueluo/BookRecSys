<template>
  <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../assets/logo.png" />
      </div>
      <el-form ref="loginFormRef" :model="loginForm" :rules="LoginRules" class="el-form_box">
        <el-form-item prop="userName">
          <el-input v-model="loginForm.userName" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <div class="button_box">
          <el-button @click="login" type="primary">Login</el-button>
          <el-button @click="register" type="primary">Register</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      loginForm: {
        userName: "",
        password: ""
      },
      LoginRules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 5, max: 15, message: "长度在 5 到 15 个字符", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    login() {
      this.$refs.loginFormRef.validate(async valid => {
        if (!valid) return;
        const result = await this.$http.post(
          "/api/login",
          JSON.stringify(this.loginForm)
        );
        if (result.data.code ==200) {
          this.$store.commit('changeName',result.data.data.userName)
          this.$store.commit('changeLoginState',true)
          this.$store.commit('changeUser',{
            name:result.data.data.userName,
            id:result.data.data.userId
            })
          this.$message.success({
            message: "恭喜你，登录成功!",
            type: "success",
            center: true
          });
          this.$router.push('/index/home')
        } else {
          this.$message.error({
            message: "登录失败！请重试！",
            center: true
          });
        }
      });
    },
    register() {
      this.$refs.loginFormRef.validate(async valid => {
        if (!valid) return;
        const result = await this.$http.post(
          "/api/register",
          JSON.stringify(this.loginForm)
        );
        console.log(result)
        if (result.data === "SUCCESS"){
          this.$message.success({
            message: "恭喜你，注册成功,请登录！",
            type: "success",
            center: true
          });
        }
        else {
          this.$message.error({
            message: "注册失败！请重试！",
            center: true
          });
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
.login_container {
  background-color: aquamarine;
  background-image: url(../assets/login.jpg);
  background-size: 100% 100%;
  background-repeat: no-repeat;
  height: 100%;
}
.login_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 5px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avatar_box {
    height: 130px;
    width: 130px;
    background-color: #fff;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
  .el-form_box {
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 10px 20px;
    box-sizing: border-box;
    .button_box {
      display: flex;
      justify-content: flex-end;
    }
  }
}
</style>