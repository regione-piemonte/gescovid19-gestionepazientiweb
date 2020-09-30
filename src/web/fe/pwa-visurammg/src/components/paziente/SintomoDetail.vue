<template>
  <modal name="sintomo-detail" :adaptive='true' height="auto" :scrollable="true"  :styles="'display: flex;padding: 12px;background-color: transparent;box-shadow: none' " @before-open="beforeOpen">
    <div class='modal-content'>
        <div class="close"  @click="$modal.hide('sintomo-detail')">
            &times;
        </div>
        <div class='modal-title'>Dettaglio Sintomo</div>
        <div class='dettaglio-sintomo' v-if='s'>
            <div class='dettaglio-row'><label>Luogo paziente</label> <span :inner-html.prop="d.luogoPaziente | emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Contesto - Contatto</label> <span :inner-html.prop="d.descrizioneContesto | emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Data esordio sintomi malattia</label> <span>{{d.dataInizioSint| formatMillis}}</span></div>
            <div class='dettaglio-row'><label>Temperatura</label> <span :inner-html.prop="s.temperatura | decodeTemperatura | emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Tosse</label> <span :inner-html.prop="s.flgTosse| decodeSintomo| emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Dispnea</label> <span :inner-html.prop="s.flgDispnea| decodeSintomo| emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Congiuntivite</label> <span :inner-html.prop="s.flgCongiuntivite| decodeSintomo| emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Diarrea</label> <span :inner-html.prop="s.flgDiarrea| decodeSintomo| emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Dolori Muscolari</label> <span :inner-html.prop="s.flgDoloriMusc| decodeSintomo| emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Gusto</label> <span :inner-html.prop="s.flgGusto| decodeSintomo| emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Olfatto</label> <span :inner-html.prop="s.flgOlfatto| decodeSintomo| emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Raffraddore</label> <span :inner-html.prop="s.flgRaffreddore| decodeSintomo| emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Stanchezza</label> <span :inner-html.prop="s.flgStanchezza| decodeSintomo| emptyhtml"></span></div>
            <div class='dettaglio-row'><label>Altro</label> <span :inner-html.prop="s.noteSintomo | emptyhtml"></span></div>
        </div>
        <div class='modal-footer ' >
          <div class='toolbar-right'>
            <a href  @click.prevent="$modal.hide('sintomo-detail')" class='btn btn-default'>Chiudi</a>
          </div>
        </div>
    </div>
  </modal>
</template>

<script>
export default {
  name: 'DettaglioSintomo',
  data: function () {
    return {
      d: null,
      s: null
    }
  },
  // computed: {
  //   d: function () {
  //     return this.decorso
  //   },
  //   s: function () {
  //     return this.sintomo
  //   }
  // },
  methods: {
    beforeOpen (event) {
      console.log('beforeOpen', event)
      this.d = event.params.decorso
      if (event.params.decorso.sintomo) {
        this.s = event.params.decorso.sintomo
      } else {
        this.s = null
      }
    }
  }
}
</script>

<style scoped>
    .modal-content{display: table; border-spacing:0 16px;padding-bottom: 15px;}
    .modal-title{font-size: 1.6em;padding-bottom: 6px; border-bottom: solid 1px #ccc; margin-bottom: 6px; width:100%;}
    .dettaglio-row{ display: table-row;}
    label{padding-right: .5em; font-weight: bold;display: table-cell;margin: 8px;width: 1%; border-bottom: solid .7px #ddd; white-space: nowrap;}
    span{display: table-cell;margin: 8px; border-bottom: solid .7px #ddd;}
    .modal-footer{justify-content: flex-end;}
    .temperatura{display: flex;}
    .temperatura-deg{flex-grow: 1;}
</style>
