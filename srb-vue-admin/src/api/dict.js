// @ 符号在vue.config.js 中配置， 表示 'src' 路径的别名
import request from '@/utils/request'

export default {
  listByParentId(parentId) {
    return request({
      url: `/admin/core/dict/listByParentId/${parentId}`,
      method: 'get'
    })
  },
}
