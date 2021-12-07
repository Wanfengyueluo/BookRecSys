import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import Login from './components/Login.vue'
import Home from './components/Home.vue'
import Rank from './components/Rank.vue'
import Bookrack from './components/Bookrack.vue'
import store from './vuex/store.js'

import axios from 'axios'

import './assets/css/global.css'
Vue.config.productionTip = false
    // axios.defaults.baseURL = "http://192.168.43.152:8080/"
axios.defaults.baseURL = "http://192.168.2.2:8080/"
    // axios.defaults.baseURL = "http://192.168.43.152:8080/"
axios.defaults.headers.post['Content-Type'] = "application/json"
Vue.prototype.$http = axios
Vue.prototype.window = window

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')