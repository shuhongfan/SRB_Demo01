// @ 符号在vue.config.js 中配置， 表示 'src' 路径的别名
import request from '@/utils/request'

export default {
  listPage(page,limit,keyword) {
    return request({
      url: `/admin/core/borrowInfo/list/${page}/${limit}`,
      method: 'get',
      params: {keyword}
    })
  },
  show(id) {
    return request({
      url: `/admin/core/borrowInfo/show/${id}`,
      method: 'get'
    })
  },
  approval(borrowInfoApproval) {
    return request({
      url: `/admin/core/borrowInfo/approval`,
      method: 'post',
      data: borrowInfoApproval
    })
  },
}
