//声明变量

//1、let定义的变量不可以重复声明、var可以
// let a
// a = 1
// let a = true

// console.log(a)

// 2、let具备块级作用域，var没有块级作用域
// var flag = true //行程是否结束
// if (flag) {
//   //块级作用域
//   let star = 5
// }
// console.log(star)

//var和let都具备函数级别的作用域
// function test1() {
//   var f1 = '函数test1的变量'
//   console.log(f1)
// }

// test1()
// console.log(f1)

//3、let不存在变量提升的特性，var存在变量提升
console.log(username) //ReferenceError: username is not defined
let username
