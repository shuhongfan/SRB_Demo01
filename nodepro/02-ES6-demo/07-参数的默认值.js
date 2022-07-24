//ES6 允许给函数参数赋值初始值
//1. 形参初始值 具有默认值的参数
function add(a, b, c = 0) {
    return a + b + c
}

// javascript没有重载
// function add(a,b){
//
// }

let result = add(1, 2)
console.log(result)

//2. 与解构赋值结合
function connect({host = '127.0.0.1', username, password, port}) {
    console.log(host)
    console.log(username)
    console.log(password)
    console.log(port)
}

connect({
    host: 'atguigu.com',
    username: 'root',
    password: 'root',
    port: 3306,
})