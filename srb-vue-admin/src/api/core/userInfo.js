// @ 符号在vue.config.js 中配置， 表示 'src' 路径的别名
import request from '@/utils/request'

export default {
  listPage(page, limit, searchObj) {
    return request({
      url: `/admin/core/userInfo/list/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  lock(id, status) {
    return request({
      url: `/admin/core/userInfo/lock/${id}/${status}`,
      method: 'put'
    })
  },
  listTop50(userId) {
    return request({
      url: `/admin/core/userLoginRecord/listTop50/${userId}`,
      method: 'get'
    })
  },
}
