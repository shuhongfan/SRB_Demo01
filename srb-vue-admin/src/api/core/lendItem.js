import request from '@/utils/request'

export default {
  list(lendId) {
    return request({
      url: `/admin/core/lendItem/list/${lendId}`,
      method: 'get'
    })
  }
}
