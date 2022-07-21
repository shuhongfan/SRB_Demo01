//1、数组的解构
const F4 = ['小沈阳', '宋小宝', '刘能', '赵四']
console.log(F4)
// let shenyang = F4[0]
// let xiaobao = F4[1]
let [shenyang, xiaobao, liuneng, zhaosi] = F4

console.log(shenyang)
console.log(xiaobao)
console.log(liuneng)
console.log(zhaosi)

//2、对象解构
const zbs = {
  username: '赵本山',
  age: '不详',
  yanxiaopin: function () {
    console.log('演小品')
  },
  sex: '男',
  hometown: '辽宁',
}

// let username1 = zbs.username
// let age1 = zbs.age
// let yanxiaopin1 = zbs.yanxiaopin
let { username } = zbs

console.log(username)
