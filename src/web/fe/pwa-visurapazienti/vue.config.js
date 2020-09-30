module.exports = {
  publicPath: '/visurapazientiweb/',
  assetsDir: process.env.BASE_URL,
  pwa: {
    workboxOptions: {
      // Dovrebbe dire al plugin di non inserire nulla nel precache
      include: []
    }
  }
}
