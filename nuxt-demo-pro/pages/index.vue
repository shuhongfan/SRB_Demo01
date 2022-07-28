<template>
  <div>
    <NuxtLink to="/about">
      关于我们
    </NuxtLink>
    <NuxtLink to="/lend">
      我要投资
    </NuxtLink>
    <NuxtLink to="/user">
      用户中心
    </NuxtLink>
    <a href="http://atguigu.com" target="_blank">尚硅谷</a>

    <h1>ip: {{ip}}</h1>
    <h1>ip1: {{ip1}}</h1>
    主页面
  </div>
</template>

<script>
export default {
  name: 'IndexPage',
  data() {
    return{
      ip:null
    }
  },
  // 服务器渲染
  async asyncData(page) {
    console.log('page',page)
    let res= await page.$axios.$get('http://icanhazip.com')
    return {
      ip1:res
    }
  },
  async created() {
    let res= await this.$axios.$get('http://icanhazip.com')
    this.ip=res
  }
}
</script>
