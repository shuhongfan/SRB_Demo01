//展开对象(拷贝对象)
let person = {name: '路飞', age: 17}

// let someone = person //引用赋值
let someone = {...person} //对拷拷贝
someone.name = '索隆'
console.log(person)
console.log(someone)