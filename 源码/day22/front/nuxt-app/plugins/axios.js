export default function({ $axios, redirect }) {
  $axios.onRequest((config) => {
    console.log('执行请求拦截器 ' + config.url)
  })

  $axios.onResponse((response) => {
    console.log('执行响应拦截器')
    return response
  })

  $axios.onError((error) => {
    console.log(error) // for debug
  })
}
