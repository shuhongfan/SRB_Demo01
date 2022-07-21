import request from '@/utils/request'

export default {
  getPageList(page, limit, searchObj) {
    return request({
      url: `/admin/core/userInfo/list/${page}/${limit}`,
      method: 'get',
      // data: searchObj //在请求体中传递json
      params: searchObj
    })
  }
}
