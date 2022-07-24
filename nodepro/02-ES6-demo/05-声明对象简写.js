let username = 'Tom'
let age = 2
let sing = function () {
    console.log('I love Jerry')
}

// 传统
let person1 = {
    username: username,
    age: age,
    sing: sing,
}
console.log(person1)
person1.sing()

// ES6：这样的书写更加简洁
let person2 = {
    age,
    username,
    sing,
}
console.log(person2)
person2.sing()