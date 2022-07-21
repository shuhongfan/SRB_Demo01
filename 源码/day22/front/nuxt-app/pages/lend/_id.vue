<template>
  <div>这个投资产品是：{{ id }}</div>
</template>

<script>
export default {
  data() {
    return {
      id: null,
    }
  },

  created() {
    this.id = this.$route.params.id
  },
}
</script>
