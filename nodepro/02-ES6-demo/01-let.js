//声明变量
let a
let b,c,d
let e = 100
let f = 521, g = 'iloveyou', h = []

//1. 变量不能重复声明
let name = 'Helen'
let name = '环'//报错：SyntaxError: Identifier 'name' has already been declared

//2. 存在块儿级作用域
// if else while for
{
    let star = 5
}
console.log(star)//报错：star is not defined

//3. 不存在变量提升
console.log(song)//报错：Cannot access 'song' before initialization
let song = '依然爱你';