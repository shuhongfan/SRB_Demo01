<template>
  <div class="app-container">
    <!-- 输入表单 -->
    <el-form label-width="120px">
      <el-form-item label="借款额度">
        <el-input-number v-model="integralGrade.borrowAmount" :min="0"/>
      </el-form-item>
      <el-form-item label="积分区间开始">
        <el-input-number v-model="integralGrade.integralStart" :min="0"/>
      </el-form-item>
      <el-form-item label="积分区间结束">
        <el-input-number v-model="integralGrade.integralEnd" :min="0"/>
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate()"
        >
          保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import core from "@/api/core";

export default {
  name: 'form',
  data() {
    return {
      integralGrade: {
        id: null,
        borrowAmount: 0,
        integralStart: 0,
        integralEnd: 0
      }, // 初始化数据
      saveBtnDisabled: false // 保存按钮是否禁用，防止表单重复提交
    }
  },
  created() {
    let id = this.$route.params.id;
    if (id) {
      this.fetchDataById(id)
    }
  },
  methods: {
    saveData() {
      core.save(this.integralGrade)
        .then(res => {
          // this.$message({
          //   type: 'success',
          //   message: res.message
          // })
          this.$message.success(res.message)
          this.$router.push('/core/integral-grade')
        })
    },
    saveOrUpdate() {
      // 禁用保存按钮
      this.saveBtnDisabled = true;
      if (this.integralGrade.id) {
        this.updateData()
      } else {
        this.saveData();
      }
    },
    // 根据id查询记录
    fetchDataById(id) {
      core.getById(id)
        .then(res => {
          this.integralGrade = res.data.record;
        })
    },
    // 根据id更新记录
    updateData() {
      core.updateById(this.integralGrade)
        .then(res => {
          this.$message.success(res.message);
          this.$router.push('/core/integral-grade')
        })
    }
  }
}
</script>

<style scoped>

</style>
