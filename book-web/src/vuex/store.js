import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
    userName: null,
    loginState: false,
    user: {
        name: '',
        id: 0
    }
}

const mutations = {
    changeName(state, name) {
        state.userName = name
    },
    changeLoginState(state, loginState) {
        state.loginState = loginState
    },
    changeUser(state, user) {
        state.user.name = user.name
        state.user.id = user.id
    }
}
const getters = {
    getName(state) {
        return state.userName
    },
    getLoginState(state) {
        return state.loginState
    },
    getUser(state) {
        return state.user
    }
}
const actions = {
    setName({ commit, state }, name) {
        commit("changeName", name)
    }
}
export default new Vuex.Store({
    state,
    mutations,
    getters,
    actions
})