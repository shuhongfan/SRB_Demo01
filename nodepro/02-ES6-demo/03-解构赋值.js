//ES6 允许按照一定模式从数组和对象中提取值，对变量进行赋值，
//这被称为解构赋值。
//1. 数组的解构
const F4 = ['小沈阳','刘能','赵四','宋小宝']
let [xiao, liu, zhao, song] = F4
console.log(xiao)
console.log(liu)
console.log(zhao)
console.log(song)

//2. 对象的解构
const zbs = {
    username: '赵本山',
    age: '不详',
    xiaopin: function(){
        console.log("演小品");
    }
}

let {username, age, xiaopin} = zbs
console.log(username)
console.log(age)
console.log(xiaopin)
xiaopin()

//3. 根据名字自动解构
// let {xiaopin} = zbs
// xiaopin()