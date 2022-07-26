// @ 符号在vue.config.js 中配置， 表示 'src' 路径的别名
import request from '@/utils/request'

export default {
  listPage(page,limit,keyword) {
    return request({
      url: `/admin/core/lend/list/${page}/${limit}`,
      method: 'get',
      params: {keyword}
    })
  },
  show(id) {
    return request({
      url: `/admin/core/lend/show/${id}`,
      method: 'get'
    })
  },
  makeLoan(id) {
    return request({
      url: `/admin/core/lend/makeLoan/${id}`,
      method: 'get'
    })
  },
}
