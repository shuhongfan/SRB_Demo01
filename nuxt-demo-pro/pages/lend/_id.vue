<template>
  <h1>投资产品 {{ id }}</h1>
</template>

<script>
export default {
  name: "_id",
  data() {
    return{
      id:null
    }
  },
  created() {
    this.id = this.$route.params.id
  }
}
</script>

<style scoped>

</style>
