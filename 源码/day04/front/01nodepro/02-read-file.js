// 引入Node.js文件系统模块：
// fs是Node.js自带的模块，使用Node.js中的关键字require将模块引入，使用const定义模块常量
const fs = require('fs') //文件系统模块

//调用readFile方法读取磁盘文件：异步操作
fs.readFile('./他.txt', function (err, data) {
  //当文件读取失败时，可以获取到err的值，输出错误信息
  if (err) throw err
  //当文件读取成功时，可以获取到data的值，输出响应的内容
  console.log(data.toString())
})
console.log('读取文件')
