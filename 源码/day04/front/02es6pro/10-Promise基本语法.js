//异步编程解决方案：文件的读取、ajax等等
const fs = require('fs') //引入node.js中的本地文件扩展模块

//实例化Promise
//Promise对象有三个状态：初始化状态、成功、失败
//resolve：函数类型的参数，可以将promise的状态设置为成功
//reject：函数类型的参数，可以将promise的状态设置为失败
const p = new Promise((resolve, reject) => {
  //执行异步操作
  //第一个参数：读取的文件的路径
  //第二个参数：读取过程中对响应结果的处理
  fs.readFile('./他1.txt', (err, data) => {
    //当文件读取失败时的错误对象
    if (err) {
      reject(err) //将Promise的状态改为失败
    }

    //当文件读取成功时的文件内容
    resolve(data) //将Promise的状态改为成功
  })
})

// p.then 当Promise的状态为成功时，then被调用
// p.catch() 当Promise的状态为失败时，catch被调用
p.then((response) => {
  //成功业务逻辑
  console.log(response.toString())
}).catch((error) => {
  //失败业务逻辑
  console.log('出错了')
  console.log(error)
})
