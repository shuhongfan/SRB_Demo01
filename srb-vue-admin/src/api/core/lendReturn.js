import request from '@/utils/request'

export default {
  list(lendId) {
    return request({
      url: `/admin/core/lendReturn/list/${lendId}` ,
      method: 'get'
    })
  }
}
