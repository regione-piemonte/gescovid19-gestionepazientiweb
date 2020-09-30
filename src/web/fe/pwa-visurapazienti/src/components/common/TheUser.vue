<template>
  <div class='userdata'  v-if="currentUser!=null">
    <div class='user'>
      <div class='username'>{{currentUser.nome + ' ' + currentUser.cognome | string_ellipse(12)}}</div>
      <div class='userinfo'>{{currentUser.sindaco.comune.nomeComune  | string_ellipse(20)}}</div>
    </div>
    <div class='logout-menu'>
      <div  @click='logout()' title="ESCI">
        <font-awesome-icon :icon="iconArrowLeft" />
        <span class='logout-label'>ESCI</span>
      </div >
    </div>
  </div>
</template>

<script>
import Constants from '@/util/constants'
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons'
export default {
  name: 'TheUser',
  components: {
  },
  data () {
    return {
      iconArrowLeft: faSignOutAlt,
      currentUser: null,
      urlLogout: '',
      loading: true,
      errored: false
    }
  },
  methods: {
    logout: function () {
      console.log('logout')
      this.$http
        .get(Constants.VISURAPAZIENTI_BASE_URL + '/localLogout')
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
    }
  },
  mounted () {
    console.log('getCurrentUser!')
    this.currentUser = null
    this.$http
      .get(Constants.VISURAPAZIENTI_BASE_URL + '/currentUser')
      .then(response => {
        const isAbilitato = function (cu) {
          const elencoProfilo = cu && cu.elencoProfilo
          if (!elencoProfilo | !elencoProfilo.length) {
            return false
          }
          for (var i = 0; i < elencoProfilo.length; i++) {
            const prof = elencoProfilo[i]
            console.log(prof)
            if (prof.idProfilo === 8) {
              return true
            }
          }
          return false
        }

        console.log('getCurrentUser', Constants.VISURAPAZIENTI_BASE_URL + '/currentUser')
        if (!response.data || !response.data.cfUtente) {
          console.log('getCurrentUser Sporco')
          var _c = new Date().getTime()
          window.location.href = '/visurapazientiweb/relogin.html?c=' + _c + '0'
        }
        console.log('getCurrentUser Pulito')
        this.currentUser = response.data

        if (!isAbilitato(this.currentUser)) {
          window.location.href = '/visurapazientiweb/accessdenied.html'
        }

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
          window.location.href = '/visurapazientiweb/relogin.html?c=' + _c + '0'
        }
        this.loading = false
      })
  }
}
</script>

<style scoped>
    .userdata{display: flex; align-items: center;}
    .user{flex-grow: 1;}
    .userinfo{font-size: .7em;}
    .logout-menu {
      padding-left: 6px;
      padding-right: 6px;
      color: #fff;
      text-transform: uppercase;
      text-align: right;
      margin-left: 1em;
    }
    .logout-menu:hover{cursor: pointer;}
    .logout-label{text-decoration: none;margin-left: .5em;}

    @media only screen and (max-width: 768px) {
      .logout-label{display: none;}
      .logout-menu{margin-left: 0;}
    }
</style>
