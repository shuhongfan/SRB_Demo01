// ES6 引入新的声明字符串的方式 『``』 '' ""
//1. 声明
let str = `我也是一个字符串哦!`
console.log(str, typeof str)

//2. 内容中可以直接出现换行符
let list = `<ul>
            <li>沈腾</li>
            <li>玛丽</li>
            <li>魏翔</li>
            <li>艾伦</li>
            </ul>`
console.log(list)

//3. 变量拼接
let lovest = '贾玲'
let out = `我喜欢${lovest}`
console.log(out)