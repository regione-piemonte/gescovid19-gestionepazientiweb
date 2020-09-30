module.exports = {
  publicPath: '/visurammgweb/',
  assetsDir: process.env.BASE_URL,
  pwa: {
    workboxOptions: {
      // Dovrebbe dire al plugin di non inserire nulla nel precache
      include: []
    }
  }
}
