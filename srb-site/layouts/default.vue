<template>
  <div>
    <app-header />
    <Nuxt />
    <AppFooter />
  </div>
</template>
<script>
import '~/assets/css/common.css'
import AppHeader from '~/components/AppHeader'
import AppFooter from '~/components/AppFooter'

export default {
  components: {
    AppHeader,
    AppFooter,
  },
}
</script>
