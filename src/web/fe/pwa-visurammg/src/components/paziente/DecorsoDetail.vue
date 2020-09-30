<template>
  <modal name="decorso-detail" :adaptive='true' height="auto" :scrollable="true" :styles="'display: flex; height: auto;padding: 12px;background-color: transparent;box-shadow: none' " @before-open="beforeOpen">
    <div class='modal-content'>
      <div class="close"  @click="$modal.hide('decorso-detail')">
          &times;
      </div>
      <div class='modal-title'>Dettaglio decorso</div>
      <div class='dettaglio-decorso' v-if='d'>
          <div class='dettaglio-row'><label>Data</label> <span :inner-html.prop="d.dataDimissioni| formatMillis| emptyhtml"></span></div>
          <div class='dettaglio-row'><label>Evento</label> <span :inner-html.prop="(d.decodeTipoEvento||{}).descTipoEvento | emptyhtml"></span></div>
          <div class='dettaglio-row'><label>Fine quarantena</label> <span :inner-html.prop="d.dataPrevFineEvento|formatMillis|emptyhtml"></span></div>
          <div class='dettaglio-row'><label>Condizioni cliniche</label> <span :inner-html.prop="d.condizioniCliniche | emptyhtml"></span></div>
          <div class='dettaglio-row'><label>Luogo paziente</label> <span :inner-html.prop="d.luogoPaziente | emptyhtml"></span></div>
          <div class='dettaglio-row'><label>Contesto - Contatto</label> <span :inner-html.prop="d.descrizioneContesto | emptyhtml"></span></div>
          <div class='dettaglio-row'><label>Note</label> <span :inner-html.prop="d.note | emptyhtml"></span></div>
          <div class='dettaglio-section-title'>Sintomi Covid</div>
          <div class='dettaglio-row'><label>Sintomi</label> <span> {{d.sintomi}}</span></div>
          <div class='dettaglio-row'><label>Data esordio sintomi malattia</label> <span :inner-html.prop="d.dataInizioSint|formatMillis| emptyhtml"></span></div>
          <div v-if='s!=null'>
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
      </div>
      <div class='modal-footer ' >
        <div class='toolbar-btn'>
          <a href  @click.prevent="$modal.hide('decorso-detail')">Chiudi</a>
        </div>
      </div>
    </div>

  </modal>
</template>

<script>
export default {
  name: 'DettaglioDecorso',
  data: function () {
    return {
      decorso: null,
      d: null,
      s: null
    }
  },
  // computed: {
  //   d: function () {
  //     return this.decorso
  //   },
  //   s: function () {
  //     console.log('sssss', this.sintomo)
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
    .modal-content{font-size: .8em; border-spacing:0 16px;padding-bottom: 15px;box-sizing: border-box}
    .modal-title{font-size: 1.6em; ;}
    .dettaglio-section-title{font-size: 1.6em; margin-top: 12px;}
    .dettaglio-row{margin: 12px 0; padding: 12px 0;  border-bottom: solid .7px #ddd;}
    label{padding-right: 0; font-weight: bold;display: block;;}
    span{display: block;margin-top: 4px;margin-right: 1em;}
    .modal-footer{flex-direction: column; padding: 12px; border:none;}
    .toolbar-btn{ box-sizing: border-box; width: 100%;height: auto;margin-top: 12px;padding: 12px 0;}
  @media only screen and (max-width: 768px) {

  }
</style>
