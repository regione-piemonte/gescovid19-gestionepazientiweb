import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import axios from 'axios'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import moment from 'moment'
import VModal from 'vue-js-modal'
import store from './util/store'

library.add(faUserSecret)

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false
Vue.prototype.$http = axios

Vue.filter('string_ellipse', function (text, length, suffix) {
  if (text.length > length) {
    if (!suffix) {
      suffix = '...'
    }
    return text.substring(0, length) + suffix
  } else {
    return text
  }
})

Vue.filter('tipoEventoShort', function (text) {
  if (text) {
    return text.replace('Dimesso in', '')
  } else {
    return text
  }
})

Vue.filter('formatMillis', function (value) {
  if (value) {
    return moment(String(new Date(value))).format('DD/MM/YY')
  }
})

Vue.filter('formatDate', function (value) {
  if (value) {
    return moment(String(value)).format('DD/MM/YYYY')
  }
})

Vue.filter('formatDateTime', function (value) {
  if (value) {
    return moment(String(value)).format('MM/DD/YYYY HH:mm')
  }
})

Vue.use(VModal)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
