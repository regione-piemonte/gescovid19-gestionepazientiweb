<template>
  <modal
    :name="nome"
    adaptive
    scrollable
    height="auto"
    width="90%"
    :styles="'display: flex;padding: 12px;background-color: transparent;box-shadow: none' "
    @before-open="beforeOpen"
  >
    <div class='modal-content'>
      <div class="close" @click="$modal.hide(nome)">
        &times;
      </div>

      <div class='modal-title pa-md'>
        Dettaglio richiesta di intervento per {{soggettoNome | empty}}
      </div>

      <!-- TABS -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <div class='tab-buttons'>
        <div class='tab-button' :class="{ 'active': activeTab === TABS.RICHIESTA}" @click="chooseTab(TABS.RICHIESTA)">
          Richiesta
        </div>
        <div class='tab-button' :class="{ 'active': activeTab === TABS.INTERVENTO}" @click="chooseTab(TABS.INTERVENTO)">
          Intervento
        </div>
        <div class='tab-button' :class="{ 'active': activeTab === TABS.CRONOLOGIA}" @click="chooseTab(TABS.CRONOLOGIA)">
          Cronologia
        </div>
        <div class='tab-button-empty'>&nbsp;</div>
      </div>

      <!-- TAB RICHIESTA -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <div class='tab-content ' :class="{ 'active': activeTab === TABS.RICHIESTA}">
        <scheda-dettaglio-richiesta :scheda="scheda"/>
      </div>

      <!-- TAB INTERVENTO -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <div class='tab-content ' :class="{ 'active': activeTab === TABS.INTERVENTO}">
        <scheda-dettaglio-intervento-lista :scheda="scheda" />
      </div>

      <!-- TAB CRONOLOGIA -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <div class='tab-content ' :class="{ 'active': activeTab ===TABS.CRONOLOGIA}">
        <scheda-dettaglio-cronologia :scheda="scheda" />
      </div>

      <!-- FOOTER -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <div class='modal-footer pa-md'>
        <div class='toolbar-right'>
          <a href @click.prevent="$modal.hide(nome)" class='btn btn-default'>Chiudi</a>
        </div>
      </div>
    </div>
  </modal>
</template>

<script>

import SchedaDettaglioRichiesta from './SchedaDettaglioRichiesta'
import SchedaDettaglioInterventoLista from './SchedaDettaglioInterventoLista'
import SchedaDettaglioCronologia from './SchedaDettaglioCronologia'

const TABS = {
  RICHIESTA: 'RICHIESTA',
  INTERVENTO: 'INTERVENTO',
  CRONOLOGIA: 'CRONOLOGIA'
}

export default {
  name: 'SchedaDettaglioModal',
  components: { SchedaDettaglioCronologia, SchedaDettaglioInterventoLista, SchedaDettaglioRichiesta },
  props: {
    nome: { type: String, required: false, default: 'scheda-detail' }
  },
  data () {
    return {
      TABS,
      activeTab: TABS.RICHIESTA,
      scheda: null
    }
  },
  computed: {
    intervento () {
      return this.scheda && this.scheda.intervento
    },
    cronologia () {
      return (this.scheda && this.scheda.cronologia) || []
    },
    soggetto () {
      return (this.scheda && this.scheda.soggetto) || {}
    },
    soggettoNome () {
      const nome = this.soggetto.nome || ''
      const cognome = this.soggetto.cognome || ''
      if (!nome && !cognome) return ''
      return [cognome, nome].join(' ')
    }
  },
  methods: {
    beforeOpen (event) {
      console.log('beforeOpen', event)
      this.scheda = event.params.scheda
    },
    chooseTab (activeTab) {
      this.activeTab = activeTab
    }
  }
}
</script>

<style scoped>
  .modal-content {
    display: table;
    border-spacing: 0 16px;
    padding-bottom: 15px;
  }

  .modal-title {
    font-size: 1.6em;
    padding-bottom: 6px;
    border-bottom: solid 1px #ccc;
    margin-bottom: 6px;
    width: 100%;
  }

  .modal-footer {
    justify-content: flex-end;
  }

  .tab-buttons {
    display: flex;
    font-size: 14px;
    line-height: 36px;
    border-bottom: solid 1px #ddd;
    width: 100%;
    justify-content: left;
  }

  .tab-button {
    padding: 5px 15px 3px 15px;
    font-weight: bold;
    border-bottom: solid 3px transparent;
    color: #9d9d9d;
  }

  .tab-button-empty {
    flex-grow: 1;
    padding: 5px 15px 3px 15px;
    font-weight: bold;
    border-bottom: solid 3px transparent;
  }

  .tab-button:hover {
    color: #e30613;
    border-color: #e30613;
    cursor: pointer;
  }

  .tab-button.active {
    color: #e30613;
    border-color: #e30613;
    cursor: pointer;
  }

  .tab-content {
    padding: 15px;
    width: 100%;
    display: none;
    box-sizing: border-box;
    background-color: #fafafa;
  }

  .tab-content.active {
    display: block;
  }

  @media only screen and (max-width: 768px) {
      .modal-title{font-size: 1.1em; padding-right: 32px; width: auto; line-height: 1.4em;}
      .modal-content{padding:0; display: block;}
      dt, dd {font-size: 12px;}
      .modal-footer {justify-content: center};
      .modal-footer .btn{display: block;}
  }

</style>
