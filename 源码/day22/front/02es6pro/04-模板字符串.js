// 1、声明一个字符串
let str1 = '我是字符串' //双引号和单引号都可以声明一个普通的字符串
let str2 = `我也是一个字符串哦`
console.log(str1)
console.log(str2)

// 2、原样输出
let htmlStr = `<ul>
<li>沈腾</li>
<li>玛丽</li>
</ul>`
console.log(htmlStr)

// 3、变量拼接（插值表达式）
let star = '贾玲'
let out = `我喜欢${star}这个演员`
console.log(out)
