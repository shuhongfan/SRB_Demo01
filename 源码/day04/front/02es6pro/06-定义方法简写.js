let person = {
  sayHi: function () {
    console.log('Hi')
  },
  sing: function () {
    console.log('一闪一闪亮晶晶')
  },
}

person.sayHi()
person.sing()

let person1 = {
  sayHi() {
    console.log('hello')
  },
  sing() {
    console.log('满天都是小星星')
  },
}

person1.sayHi()
person1.sing()
