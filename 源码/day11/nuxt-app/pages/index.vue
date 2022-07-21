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

    <h1>主页面</h1>
    <p>您的ip是 {{ ip1 }}</p>
  </div>
</template>

<script>
export default {
  //服务器端数据渲染
  async asyncData({ $axios }) {
    console.log('axios', $axios)

    //同步操作
    let response = await $axios.$get('/')
    return {
      ip1: response, //response未定义
    }
  },

  data() {
    return {
      ip: null,
    }
  },

  created() {
    // get
    // this.$axios.get('http://icanhazip.com/').then((response) => {
    //   console.log(response)
    //   this.ip = response.data
    // })
    // this.$axios.$get('http://icanhazip.com/').then((response) => {
    //   console.log(response)
    //   this.ip = response
    // })
  },
}
</script>
