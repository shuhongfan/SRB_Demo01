<template>
  <div>
    <h1>用户中心</h1>

    <NuxtLink to="/user">我的账户</NuxtLink>
    <NuxtLink to="/user/user1">我的投资</NuxtLink>
    <NuxtLink to="/user/user2">我的个人信息</NuxtLink>

    <!-- Nuxt嵌套路由出口 -->
    <NuxtChild />
  </div>
</template>

<script>
export default {
  layout: 'my',
}
</script>
