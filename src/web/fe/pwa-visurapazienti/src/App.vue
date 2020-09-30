<template>
  <div id="app">
    <!--<div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>-->
    <TheHeader />
    <router-view/>
    <TheReleasenotes />
    <TheNews />
    <TheFooter />
    <!--<Home />-->
  </div>
</template>
<script>
import TheHeader from '@/components/common/TheHeader'
import TheFooter from '@/components/common/TheFooter'
import TheReleasenotes from '@/components/common/TheReleasenotes'
import TheNews from '@/components/common/TheNews'
import Constants from '@/util/constants'

// import Home from '@/views/Home'
export default {
  name: 'App',
  components: {
    TheHeader,
    TheFooter,
    TheReleasenotes,
    TheNews
  },
  methods: {
    showReleasenotesModal: function () {
      console.log('app showReleasenotesModal')
      this.$modal.show('releasenotes-modal')
    },
    showNewsModal: function (news) {
      console.log('app showReleasenotesModal', news)
      this.$modal.show('news-modal', { news: news })
    }
  },
  mounted: function () {
    if (localStorage && !localStorage['relasenotes_' + Constants.VERSION + '_readed']) {
      this.showReleasenotesModal()
    }

    this.$http
      .get('/news/visurapazienti.txt?t=' + new Date().getTime())
      .then(response => {
        console.log('getNews', response.data)
        if (response && response.data) {
          const newsSplitted = response.data.split(/\r?\n/)
          if (newsSplitted && newsSplitted.length > 1) {
            const news = { numero: newsSplitted.shift(), items: newsSplitted }
            if (!localStorage || !localStorage['news_' + news.numero + '_readed']) {
              this.showNewsModal(news)
            }
          }
        }
      })
      .catch(error => {
        console.debug(error)
      })
      .finally(this.loading = false)

    if (localStorage && !localStorage['relasenotes_' + Constants.VERSION + '_readed']) {
    }
  }
}
</script>
<style>
@import './assets/css/app.css';
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
