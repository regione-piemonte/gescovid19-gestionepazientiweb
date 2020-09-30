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
import VueSync from 'vue-sync'

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
    return moment(String(value)).format('DD/MM/YYYY HH:mm')
  }
})

Vue.filter('empty', function (value) {
  if (value) {
    return value
  } else {
    return '-'
  }
})

Vue.filter('decodeTipoSoggetto', function (value) {
  if (value) {
    return value
  } else {
    return 'cittadino'
  }
})

Vue.filter('emptyhtml', function (value) {
  if (value && value !== '-') {
    return value
  } else {
    return '<span class="emptyvalue">Valore non impostato</span>'
  }
})

Vue.filter('decodeSintomo', function (value) {
  if (value) {
    if (value === 'NO') {
      return 'No'
    } else if (value === 'SI') {
      return 'Si'
    } else if (value === 'PRIMA_EMERGENZA') {
      return 'Prima emergenza'
    } else if (value === 'ULTIME_2_SETTIMANE') {
      return 'Ultime due settimane'
    } else if (value === 'LEGGERO') {
      return 'Leggero'
    } else if (value === 'FORTE') {
      return 'Forte'
    } else if (value === 'NELLA_NORMA') {
      return 'Nella norma'
    } else if (value === 'PIU_SOLITO') {
      return 'Più del solito'
    } else if (value === 'NON_PERSISTENTE') {
      return 'Non persistente'
    } else if (value === 'PERSISTENTE_GRASSA') {
      return 'Persistente grassa'
    } else if (value === 'PERSISTENTE_SECCA') {
      return 'Persistente secca'
    } else {
      return '-'
    }
  } else {
    return '-'
  }
})

Vue.filter('decodeTemperatura', function (value) {
  if (value) {
    return value + '°'
  } else {
    return '-'
  }
})

Vue.filter('listValue', (v, prop, separator = ', ') => {
  try {
    return v.map(obj => obj[prop]).join(separator)
  } catch (e) {
    return ''
  }
})

// axios.interceptors.response.use(function (response) {
//   console.log('interceptor response', response)
//   return response
// }, function (error) {
//   console.log('interceptor response', error)
//   // Any status codes that falls outside the range of 2xx cause this function to trigger
//   // Do something with response error
//   return Promise.reject(error)
// })

Vue.use(VueSync)
Vue.use(VModal)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
