<template>
  <div class='scheda-dettaglio-richiesta'>
    <template v-if="!scheda">
      Non è presente nessuna scheda
    </template>

    <template v-else>
      <dl>
        <dt>Data richiesta</dt>
        <dd>{{dataRichiesta | formatDateTime}}</dd>

        <dt>Medico richiedente</dt>
        <dd>{{medicoRichiedenteNome | empty}}</dd>

        <dt>Destinatario</dt>
        <dd>{{destinatario | empty}}</dd>

        <dt>Descrizione</dt>
        <dd>{{descrizione | empty}}</dd>

        <template v-for="prescrizione in prescrizioni">
          <dt :key="prescrizione.id + 'nome'">{{prescrizione.tipo.nome}}</dt>
          <dd :key="prescrizione.id + 'prescrizione'">{{prescrizione.prescrizione | empty}}</dd>
        </template>
      </dl>
    </template>
  </div>
</template>

<script>
export default {
  name: 'SchedaDettaglioRichiesta',
  props: {
    scheda: { type: Object, required: false, default: () => null }
  },
  computed: {
    dataRichiesta () {
      return this.scheda && this.scheda.data
    },
    descrizione () {
      return this.scheda && this.scheda.descrizione
    },
    destinatario () {
      return this.scheda && this.scheda.usca && this.scheda.usca.nome
    },
    medicoRichiedente () {
      return (this.scheda && this.scheda.medico) || {}
    },
    medicoRichiedenteNome () {
      const nome = this.medicoRichiedente.nome || ''
      const cognome = this.medicoRichiedente.cognome || ''
      if (!nome && !cognome) return ''
      return [cognome, nome].join(' ')
    },
    prescrizioni () {
      return (this.scheda && this.scheda.prescrizioni) || []
    }
  }
}
</script>

<style scoped>

</style>
