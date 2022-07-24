//声明常量
const SCHOOL = '尚硅谷'
console.log(SCHOOL)

//1. 一定要赋初始值
const A//报错：SyntaxError: Missing initializer in const declaration

//2. 一般常量使用大写(潜规则)
const a = 100

//3. 常量的值不能修改
SCHOOL = 'ATGUIGU'//报错：TypeError: Assignment to constant variable.

console.log(PLAYER)//报错：ReferenceError: PLAYER is not defined

//4. 对于数组和对象的元素修改, 不算做对常量的修改, 不会报错
const TEAM = ['康师傅','海狗人参丸','雷神','阳哥']
TEAM.push('环') //常量地址不变，不会报错
TEAM = 100 //报错：TypeError: Assignment to constant variable.