//引入Node.js的http模块：
const http = require('http')

//调用createServer创建服务器
http
  .createServer(function (request, response) {
    // 发送 HTTP 头部
    // HTTP 状态值: 200 : OK
    // 内容类型: text/plain
    response.writeHead(200, { 'Content-Type': 'text/html' })
    // 发送响应数据 "Hello World"
    response.end('<h1>Hello Node.js Server</h1>')
  })
  .listen(8888) //调用listen方法在8888端口监听客户端请求

// 终端打印如下信息
console.log('Server running at http://127.0.0.1:8888/')
