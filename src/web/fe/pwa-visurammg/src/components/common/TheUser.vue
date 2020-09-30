<template>
<div v-if="currentUser!=null" class='userdata row items-center' style="height: 100%">
  <div class='user cursor-pointer' @click="mostraModalProfilo">

    <div class='username' style="position: relative">
      <template v-if="deveInserireContatti">
        <font-awesome-icon :icon="faExclamationCircle" size="sm" style="position: absolute; right: -14px; top: -6px" />
      </template>

      {{currentUser.medico.nome + ' ' + currentUser.medico.cognome}}
    </div>
<!--    <div>-->
<!--      <a href="#" @click.prevent="mostraModalProfilo" class="no-decoration" style="color: white; font-size: 12px">-->
<!--        Contatti-->
<!--      </a>-->
<!--    </div>-->
    <!-- <div class='userinfo'>{{currentUser.sindaco.comune.nomeComune  | string_ellipse(20)}}</div>-->
  </div>

  <div class='header-toolbar'>
    <div class='help-menu'>
        <a href='/manualigescovid/visurammgweb/Manuale_Utente_Visurammgweb.pdf' target='_blank'>
          <font-awesome-icon :icon="iconHelp" />
          <span class='help-label'>Manuale</span>
        </a>
    </div>
    <div class='logout-menu'>
      <div  @click='logout()' title="ESCI">
        <font-awesome-icon :icon="iconArrowLeft" />
        <span class='logout-label'>ESCI</span>
      </div >
    </div>
  </div>
</div>
</template>

<script>
import Constants from '@/util/constants'
import { faSignOutAlt, faQuestionCircle, faCircle, faExclamationCircle } from '@fortawesome/free-solid-svg-icons'
export default {
  name: 'TheUser',
  components: {
  },
  data () {
    return {
      iconHelp: faQuestionCircle,
      iconArrowLeft: faSignOutAlt,
      faCircle,
      faExclamationCircle,
      currentUser: null,
      urlLogout: '',
      loading: true,
      errored: false
    }
  },
  methods: {
    mostraModalProfilo () {
      this.$modal.show('profilo-utente', { utente: this.currentUser })
    },
    logout: function () {
      console.log('logout')
      this.$http
        .get(Constants.VISURAMMG_BASE_URL + '/localLogout')
        .then(response => {
          console.log('getUrlLogout', response)
          window.location = response.data.message
          console.log('urlLogout', this.urlLogout)
        })
        .catch(error => {
          console.log(error)
          this.errored = true
        })
        .finally(this.loading = false)
    },
    getCurrentUser: function () {
      console.log('getCurrentUser!')
      this.currentUser = null
      this.$http
        .get(Constants.VISURAMMG_BASE_URL + '/currentUser')
        .then(response => {
          console.log('getCurrentUser', Constants.VISURAMMG_BASE_URL + '/currentUser')
          if (!response.data || !response.data.cfUtente) {
            console.log('getCurrentUser Sporco')
            var _c = new Date().getTime()
            window.location.href = '/visurammgweb/relogin.html?c=' + _c + '0'
          }
          console.log('getCurrentUser Pulito')
          this.currentUser = response.data
          this.$store.commit('setCurrentUser', this.currentUser)
        })
        .catch(error => {
          console.log('getCurrentUserError:' + error)
          this.errored = true
        })
        .then(a => {
          console.log('getCurrentUser FINAL:')
          if (!this.currentUser) {
            console.log('getCurrentUser Vuoto')
            var _c = new Date().getTime()
            window.location.href = '/visurammgweb/relogin.html?c=' + _c + '0'
          }
          this.loading = false
        })
    }
  },
  mounted () {
    this.getCurrentUser()
  },
  computed: {
    contattiUtente () {
      return (this.currentUser && this.currentUser.contatto) || {}
    },
    deveInserireContatti () {
      return !this.contattiUtente.telefono
    }
  }
}
</script>

<style scoped>
    .userdata{display: flex; align-items: center;}
    .user{flex-grow: 1;}
    .userinfo{font-size: .7em;}
    .logout-menu {padding:0 6px;color: #fff; text-transform: uppercase; text-align: right;margin-left: 1em;}
    .logout-menu:hover{cursor: pointer;}
    .logout-label{text-decoration: none;margin-left: .5em;}

    .help-menu, .help-menu a{padding:0 6px;color: #fff; text-transform: uppercase; text-align: right;margin-left: 1em;text-decoration: none;}
    .help-menu:hover{cursor: pointer;text-decoration: none;}
    .help-label{text-decoration: none;margin-left: .5em;}
    .header-toolbar{display: flex;}

    @media only screen and (max-width: 768px) {
      .userdata{flex-direction: column;}
      .header-toolbar{padding-top: 12px;}
      .help-label{display: none;}
      .logout-label{display: none;}
      .logout-menu{margin-left: 0; padding: 0 0 0 12px;}
    }
</style>
