let person = { username: '王路飞', age: 19 }
// let someone = person //引用传递
let someone = { ...person }

console.log(person)
console.log(someone)

someone.username = '乔巴'

console.log(person)
console.log(someone)
