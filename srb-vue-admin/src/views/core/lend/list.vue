<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true">
      <el-form-item label="关键字">
        <el-input clearable v-model="keyword" placeholder="姓名/手机"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="fetchData()">
        查询
      </el-button>
      <el-button type="default" @click="resetData()">
        清空
      </el-button>
    </el-form>


    <!-- 列表 -->
    <el-table :data="list" stripe>
      <el-table-column type="index" label="序号" width="60" align="center"/>
      <el-table-column prop="lendNo" label="标的编号" width="160"/>
      <el-table-column prop="amount" label="标的金额"/>
      <el-table-column prop="period" label="投资期数"/>
      <el-table-column label="年化利率">
        <template slot-scope="scope">
          {{ scope.row.lendYearRate * 100 }}%
        </template>
      </el-table-column>
      <el-table-column prop="investAmount" label="已投金额"/>
      <el-table-column prop="investNum" label="投资人数"/>
      <el-table-column prop="publishDate" label="发布时间" width="150"/>
      <el-table-column prop="lendStartDate" label="开始日期"/>
      <el-table-column prop="lendEndDate" label="结束日期"/>
      <el-table-column prop="param.returnMethod" label="还款方式"/>
      <el-table-column prop="param.status" label="状态"/>

      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini">
            <router-link :to="'/core/lend/detail/' + scope.row.id">
              查看
            </router-link>
          </el-button>

          <el-button
            v-if="scope.row.status == 1"
            type="warning"
            size="mini"
            @click="makeLoan(scope.row.id)"
          >
            放款
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      align="center"
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[2, 10, 20]"
      style="padding: 30px 0; "
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="changePageSize"
      @current-change="changeCurrentPage"
    />
  </div>
</template>

<script>
import lend from "@/api/core/lend";

export default {
  name: "list",
  data() {
    return {
      list: null, // 列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      keyword: '', // 查询表单对象
    }
  },

  created() {
    this.fetchData()
  },

  methods: {
    // 加载列表数据
    async fetchData() {
      let res = await lend.listPage(this.page, this.limit, this.keyword);
      if (res.code === 0) {
        this.list = res.data.pageModel.records
      }
    },
    // 每页记录数改变，size：回调参数，表示当前选中的“每页条数”
    changePageSize(size) {
      this.limit = size
      this.fetchData()
    },
    // 改变页码，page：回调参数，表示当前选中的“页码”
    changeCurrentPage(page) {
      this.page = page
      this.fetchData()
    },
    // 重置表单
    resetData() {
      this.keyword = ''
      this.fetchData()
    },
    makeLoan(id) {
      this.$confirm('确定放款吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          return lend.makeLoan(id)
        })
        .then(response => {
          //放款成功则重新获取数据列表
          this.fetchData()
          this.$message({
            type: 'success',
            message: response.message
          })
        })
        .catch(error => {
          console.log('取消', error)
          if (error === 'cancel') {
            this.$message({
              type: 'info',
              message: '已取消放款'
            })
          }
        })
    },
  }
}
</script>

<style scoped>

</style>
