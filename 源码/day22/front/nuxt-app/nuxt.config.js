module.exports = {
  head: {
    title: '尚融宝',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'meta-key-words',
        name: 'keywords',
        content:
          '尚融宝官网_纽交所上市企业,网络借贷平台,解决个人小额借款,短期借款问题,资金银行存管,安全保障',
      },
      {
        hid: 'description',
        name: 'description',
        content:
          '尚融宝官网_纽交所上市企业，网络借贷平台，解决个人小额借款、短期借款问题。 资金银行存管，安全保障。',
      },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
  },

  css: [
    // CSS file in the project
    '~/assets/css/main.css',
  ],

  server: {
    port: 3001, // default: 3000
  },

  modules: [
    '@nuxtjs/axios', //引入axios模块
  ],

  axios: {
    // Axios options here
    baseURL: 'http://icanhazip.com',
  },

  plugins: ['~/plugins/axios'],
}
