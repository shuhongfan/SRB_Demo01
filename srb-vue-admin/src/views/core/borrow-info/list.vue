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
      <el-table-column prop="name" label="借款人姓名" width="90"/>
      <el-table-column prop="mobile" label="手机"/>
      <el-table-column prop="amount" label="借款金额"/>
      <el-table-column label="借款期限" width="90">
        <template slot-scope="scope">{{ scope.row.period }}个月</template>
      </el-table-column>
      <el-table-column prop="param.returnMethod" label="还款方式" width="150"/>
      <el-table-column prop="param.moneyUse" label="资金用途" width="100"/>
      <el-table-column label="年化利率" width="90">
        <template slot-scope="scope">
          {{ scope.row.borrowYearRate * 100 }}%
        </template>
      </el-table-column>
      <el-table-column prop="param.status" label="状态" width="100"/>

      <el-table-column prop="createTime" label="申请时间" width="150"/>

      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini">
            <router-link :to="'/core/borrower/info-detail/' + scope.row.id">
              查看
            </router-link>
          </el-button>

          <el-button
            v-if="scope.row.status === 1"
            type="warning"
            size="mini"
            @click="approvalShow(scope.row)"
          >
            审批
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

    <!-- 审批对话框 -->
    <el-dialog title="审批" :visible.sync="dialogVisible" width="490px">
      <el-form label-position="right" label-width="100px">
        <el-form-item label="是否通过">
          <el-radio-group v-model="borrowInfoApproval.status">
            <el-radio :label="2">通过</el-radio>
            <el-radio :label="-1">不通过</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item v-if="borrowInfoApproval.status == 2" label="标的名称">
          <el-input v-model="borrowInfoApproval.title"/>
        </el-form-item>

        <el-form-item v-if="borrowInfoApproval.status == 2" label="起息日">
          <el-date-picker
            v-model="borrowInfoApproval.lendStartDate"
            type="date"
            placeholder="选择开始时间"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>

        <el-form-item v-if="borrowInfoApproval.status == 2" label="年化收益率">
          <el-input v-model="borrowInfoApproval.lendYearRate">
            <template slot="append">%</template>
          </el-input>
        </el-form-item>

        <el-form-item v-if="borrowInfoApproval.status == 2" label="服务费率">
          <el-input v-model="borrowInfoApproval.serviceRate">
            <template slot="append">%</template>
          </el-input>
        </el-form-item>

        <el-form-item v-if="borrowInfoApproval.status == 2" label="标的描述">
          <el-input v-model="borrowInfoApproval.lendInfo" type="textarea"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="approvalSubmit">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import borrowInfo from "@/api/core/borrowInfo";

export default {
  name: "list",
  data() {
    return {
      list: null, // banner列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      keyword: '', // 查询表单对象
      dialogVisible: false, //审批对话框
      borrowInfoApproval: {
        status: 2,
        serviceRate: 5,
        lendYearRate: 0 //初始化，解决表单中数据修改时无法及时渲染的问题
      } //审批对象
    }
  },
  created() {
    this.fetchData();
  },
  methods: {
    async fetchData() {
      let res = await borrowInfo.listPage(this.page, this.limit, this.keyword);
      if (res.code === 0) {
        this.list = res.data.pageModel.records
        this.total = res.data.pageModel.total
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
    // 审批弹出框
    approvalShow(row) {
      this.dialogVisible = true
      this.borrowInfoApproval.id = row.id
      this.borrowInfoApproval.lendYearRate = row.borrowYearRate * 100
    },
    // 提交审批
    async approvalSubmit() {
      let res = await borrowInfo.approval(this.borrowInfoApproval);
      if (res.code === 0) {
        this.dialogVisible = false
        this.$message.success(res.message)
        this.fetchData();
      }
    },
  }
}
</script>

<style scoped>

</style>
