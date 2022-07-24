const fs = require('fs')

//实例化 Promise 对象：
//Promise对象有三个状态：初始化、成功、失败
// resolve：函数类型参数，可以将promise的状态设置为成功
// reject：函数类型的参数，可以将promise的状态设置为失败
const p = new Promise((resolve, reject) => {
    //调用readFile方法读取磁盘文件：异步操作
    fs.readFile('./08-对象扩展运算符.1js', (err, data) => {
        //当文件读取失败时，可以获取到err的值
        if (err) reject(err) //reject将Promise对象的状态设置为失败

        //当文件读取成功时，可以获取到data的值
        resolve(data) //resolve将Promise对象的状态设置为成功
    })
})

//调用 promise 对象的方法
//then：当 Promise状态成功时执行
//catch：当 Promise状态失败时执行
p.then(response => { // 成功
    console.log(response.toString())
}).catch(error => { // 失败
    console.log('出错了')
    console.error(error)
})