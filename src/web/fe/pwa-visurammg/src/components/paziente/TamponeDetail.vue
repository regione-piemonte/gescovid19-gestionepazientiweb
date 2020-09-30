<template>
  <modal name="tampone-detail" :adaptive='true'  :styles="'display: flex;padding: 12px;background-color: transparent;box-shadow: none' " @before-open="beforeOpen">
    <div class='modal-content'>
        <div class="close"  @click="$modal.hide('tampone-detail')">
            &times;
        </div>
        <div class='modal-title'>Dettaglio tampone</div>
        <div class='dettaglio-tampone' v-if='t'>
            <div class='dettaglio-row'><label>Data richiesta</label> <span>{{t.dataInserimentoRichiesta| formatMillis}}</span></div>
            <div class='dettaglio-row'><label>Criterio epid.</label> <span>{{t.criterioEpidemiologicoCovid19}}</span></div>
            <div class='dettaglio-row'><label>Tipologia test covid</label> <span>{{(t.testCovid||{}).testCovid}}</span></div>
            <div class='dettaglio-row'><label>Esito tampone</label> <span>{{(t.risTampone||{}).risultatoTampone}}</span></div>
            <div class='dettaglio-row'><label>Data test</label> <span>{{t.dataTest|formatMillis}}</span></div>
        </div>
    </div>
  </modal>
</template>

<script>
export default {
  name: 'Dettagliotampone',
  data: function () {
    return {
      tampone: null
    }
  },
  computed: {
    t: function () {
      return this.tampone
    }
  },
  methods: {
    beforeOpen (event) {
      console.log('beforeOpen', event)
      this.tampone = event.params.tampone
    }
  }
}
</script>

<style scoped>
    .modal-content{font-size: .8em; display: table; border-spacing:0 16px;padding-bottom: 15px;}
    .modal-title{font-size: 1.6em;position: absolute; top: 8px; left: 10px;}
    .dettaglio-row{ display: table-row;}
    label{padding-right: .5em; font-weight: bold;display: table-cell;margin: 8px;width: 1%; border-bottom: solid .7px #ddd;}
    span{display: table-cell;margin: 8px; border-bottom: solid .7px #ddd;}
</style>
