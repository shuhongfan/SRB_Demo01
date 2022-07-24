//可以导出任意类型，但同时只能有一个export default
// export default 'helen'
// export default 99

//大部分情况是导出对象
export default {
    username: 'helen',
    age: 20,
    coding() {
        console.log('hello world')
    }
}