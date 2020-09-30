<template>
  <modal name="prescrizione-detail" :adaptive='true' height="auto" :scrollable="true"  @before-open="beforeOpen"
    :styles="'display: flex;padding: 12px;background-color: transparent;box-shadow: none' ">
    <div class='modal-content'>
        <div class="close"  @click="$modal.hide('prescrizione-detail')">
            &times;
        </div>
        <div class='modal-title'>Dettaglio Prescrizione <span v-if='prescrizione' class='lowercase'>{{prescrizione.tipo.nome}}</span></div>
        <div class='dettaglio-prescrizione' v-if='prescrizione'>
          <p>{{prescrizione.prescrizione}}</p>
        </div>
        <div class='modal-footer ' >
          <div class='toolbar-right'>
            <a href  @click.prevent="$modal.hide('prescrizione-detail')" class='btn btn-default'>Chiudi</a>
          </div>
        </div>
    </div>
  </modal>
</template>

<script>
export default {
  name: 'DettaglioPrescrizione',
  data: function () {
    return {
      prescrizione: null
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
      this.prescrizione = event.params.prescrizione
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
</style>
