<template>
  <div>
    <h1>用户中心</h1>

    <NuxtLink to="/user">
      用户信息
    </NuxtLink>

    <NuxtLink to="/user/account">
      用户账户
    </NuxtLink>

    <NuxtLink to="/user/user1">我的账户</NuxtLink>
    <NuxtLink to="/user/user2">我的个人信息</NuxtLink>

<!--    嵌套路由：如果 pages/user.vue 和 pages/user/index.vue 同时存在，我们可以利用嵌套路由-->
    <!-- user目录下的页面的路由出口 -->
    <NuxtChild />
  </div>
</template>

<script>
export default {
  name: "user",
  layout: 'my'
}
</script>

<style scoped>

</style>
