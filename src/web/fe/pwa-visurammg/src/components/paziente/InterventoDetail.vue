<template>
  <modal name="intervento-detail" :adaptive='true' height="auto" :scrollable="true"  @before-open="beforeOpen"
    :styles="'display: flex;padding: 12px;background-color: transparent;box-shadow: none' ">
    <div class='modal-content'>
        <div class="close"  @click="$modal.hide('intervento-detail')">
            &times;
        </div>
        <div class='modal-title'>Dettaglio intervento <span v-if='intervento' class='lowercase'>{{intervento.azione.nome}}</span></div>
        <div class='dettaglio-intervento' v-if='intervento'>
          <div class='label'>Data intervento</div>
          <p>{{intervento.data|formatMillis}}</p>
          <div class='label'>Tipo intervento</div>
          <p>{{(intervento.tipo||{}).nome|emtpy}}</p>
          <div class='label'>Descrizione</div>
          <p>{{intervento.descrizione|emtpy}}</p>
          <div v-for='p in intervento.prescrizioni' :key="p.id">
            <div class='lowercase label'>Prescrizione {{p.tipo.nome}}</div>
            <p>{{p.prescrizione}}</p>
          </div>
        </div>
        <div class='modal-footer ' >
          <div class='toolbar-right'>
            <a href  @click.prevent="$modal.hide('intervento-detail')" class='btn btn-default'>Chiudi</a>
          </div>
        </div>
    </div>
  </modal>
</template>

<script>
export default {
  name: 'DettaglioIntervento',
  data: function () {
    return {
      intervento: null
    }
  },
  methods: {
    debug () {
      console.log('paziente', this.paziente)
      console.log('scheda', this.scheda)
    },
    forceUpdate (event) {
      console.log('forceupdate', this.paziente)
      this.$forceUpdate()
    },
    beforeOpen (event) {
      console.log('beforeOpen', event)
      this.intervento = event.params.intervento
    }
  }
}
</script>

<style scoped>
    .modal-content{display: flex;padding-bottom: 12px; flex-direction: column;}
    .modal-title{font-size: 1.6em;padding-bottom: 6px; border-bottom: solid 1px #ccc; margin-bottom: 6px; width:100%;}
    .dettaglio-row{ display: table-row;}
    label{padding-right: .5em; font-weight: bold;display: table-cell;margin: 8px;width: 1%; border-bottom: solid .7px #ddd; white-space: nowrap;}
    .modal-footer{justify-content: flex-end;}
    .temperatura{display: flex;}
    .temperatura-deg{flex-grow: 1;}
    .label{font-weight: bold;}
    p{margin-bottom: 3em;}
</style>
