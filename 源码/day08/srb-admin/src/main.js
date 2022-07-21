import Vue from 'vue' //vue

import 'normalize.css/normalize.css' // A modern alternative to CSS resets //基础css样式设置

import ElementUI from 'element-ui' //element-ui
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css //全局css样式定义

import App from './App' //根组件
import store from './store' //前端信息存储工具
import router from './router' //路由模块

import '@/icons' // icon //扩展图标系统
import '@/permission' // permission control //角色权限控制系统

//关于模拟接口服务器的设置
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router, //挂载路由
  store,
  render: h => h(App) //组件
})
