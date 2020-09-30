<template>
  <div id="app">
    <!--<div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>-->
    <TheHeader />
    <router-view/>
    <TheReleasenotes />
    <TheFooter />
    <!--<Home />-->

    <!-- MODALS -->
    <!-- ----------------------------------------------------------------------------------------------------------- -->
    <DettaglioDecorso />
    <Dettagliotampone />
    <SegnalazionePaziente />
    <DettaglioSintomo />
    <SchedaDettaglioModal />
    <EditSchedaPaziente />
    <DettaglioPrescrizione />
    <SchedaPazienteCronologiaModal />
    <DettaglioIntervento />
    <DiarioPaginaNuovaModal />
    <UtenteProfiloModal />
    <SchedaDettaglioInterventoItemModal />
  </div>
</template>
<script>
import TheHeader from '@/components/common/TheHeader'
import TheFooter from '@/components/common/TheFooter'
import TheReleasenotes from '@/components/common/TheReleasenotes'
import Constants from '@/util/constants'
import DettaglioDecorso from './components/paziente/DecorsoDetail'
import Dettagliotampone from './components/paziente/TamponeDetail'
import SegnalazionePaziente from './components/paziente/SegnalazionePaziente'
import DettaglioSintomo from './components/paziente/SintomoDetail'
import SchedaDettaglioModal from './components/paziente/SchedaDettaglioModal'
import EditSchedaPaziente from './components/paziente/ModificaSchedaPaziente'
import DettaglioPrescrizione from './components/paziente/PrescrizioneDetail'
import SchedaPazienteCronologiaModal from './components/paziente/SchedaPazienteCronologiaModal'
import DettaglioIntervento from './components/paziente/InterventoDetail'
import DiarioPaginaNuovaModal from './components/paziente/DiarioPaginaNuovaModal'
import UtenteProfiloModal from './components/paziente/UtenteProfiloModal'
import SchedaDettaglioInterventoItemModal from './components/paziente/SchedaDettaglioInterventoItemModal'

// import Home from '@/views/Home'
export default {
  name: 'App',
  components: {
    SchedaDettaglioInterventoItemModal,
    UtenteProfiloModal,
    DiarioPaginaNuovaModal,
    DettaglioIntervento,
    SchedaPazienteCronologiaModal,
    DettaglioPrescrizione,
    EditSchedaPaziente,
    SchedaDettaglioModal,
    DettaglioSintomo,
    SegnalazionePaziente,
    Dettagliotampone,
    DettaglioDecorso,
    TheHeader,
    TheFooter,
    TheReleasenotes
  },
  methods: {
    showReleasenotesModal: function () {
      console.log('app showReleasenotesModal')
      this.$modal.show('releasenotes-modal')
    }
  },
  mounted: function () {
    if (localStorage && !localStorage['relasenotes_' + Constants.VERSION + '_readed']) {
      this.showReleasenotesModal()
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
