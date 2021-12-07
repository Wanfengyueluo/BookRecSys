import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home.vue'
import Rank from '@/components/Rank'
import Bookrack from '@/components/Bookrack'
import BookDetail from '@/components/BookDetail'

Vue.use(VueRouter)

const routes = [{
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        component: Login
    },
    {
        path: '/index/home',
        component: Home
    },
    {
        path: '/index/rank',
        component: Rank
    },
    {
        path: '/index/bookrack',
        component: Bookrack,
        meta: {
            requireAuth: true
        }
    },
    {
        path: '/index/books/item',
        component: BookDetail
    }
]

const router = new VueRouter({
    routes
})

router.beforeEach((to, from, next) => {
    next((vm) => {
        if (to.meta.requireAuth) {
            if (vm.$store.state.userName === null) {
                next('/login')
            } else {
                next()
            }
        } else {
            next()
        }
    })
})
export default router