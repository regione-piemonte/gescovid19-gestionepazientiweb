<template>
  <div class='scheda-dettaglio-intervento'>
    <template v-if="!hasInterventi">
      Non è stato ancora generato un intervento per questa richiesta.
    </template>

    <template v-else>
      <table class='table-small' width="100%">
        <thead class="table-header">
        <tr>
          <th>Data intervento</th>
          <th>Medico</th>
          <th>Tipo intervento</th>
          <th>Azione intrapresa</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="i in interventiOrdinati" :key="i.id" class="table-row">
          <td>
            {{getData(i) | formatDateTime}}
          </td>
          <td>
            <span class='cell-row'>
            {{getMedicoNome(i) | empty}}
            </span>
            <template v-if="getMedicoTelefono(i)">
              <span class='cell-row'>
                 <a :href="'tel:' + getMedicoTelefono(i)" class="no-decoration">
                  {{getMedicoTelefono(i)}}
                </a>
              </span>
            </template>
          </td>
          <td>
            {{getTipo(i) | empty}}
          </td>
          <td>
            <span class='cell-row'>
            {{getAzione(i) | empty}}
            </span>
            <span class='cell-row' style="font-style: italic" >
              {{getAzioneDescrizione(i) | empty}}
            </span>
          </td>
          <td>
            <span class='btn prescrizioni-button' @click="showDettaglioIntervento(i)">Dettaglio</span>
          </td>
        </tr>
        </tbody>
      </table>
    </template>
  </div>
</template>

<script>

export default {
  name: 'SchedaDettaglioInterventoLista',
  props: {
    scheda: { type: Object, required: false, default: () => null }
  },
  computed: {
    interventi () {
      return (this.scheda && this.scheda.intervento) || []
    },
    hasInterventi () {
      return this.interventi.length > 0
    },
    interventiOrdinati () {
      const result = [...this.interventi]

      result.sort((a, b) => {
        return new Date(a) > new Date(b) ? 1 : -1
      })

      return result
    }
  },
  methods: {
    getData (intervento) {
      return intervento.data
    },
    getTipo (intervento) {
      return intervento.tipo && intervento.tipo.nome
    },
    getAzione (intervento) {
      return intervento.azione && intervento.azione.nome
    },
    getAzioneDescrizione (intervento) {
      return intervento.azione_descrizione
    },
    getMedico (intervento) {
      return intervento.medico_responsabile
    },
    getMedicoNome (intervento) {
      const medico = this.getMedico(intervento)
      const nome = medico && medico.nome
      const cognome = medico && medico.cognome
      return [cognome, nome].join(' ').trim()
    },
    getMedicoTelefono (intervento) {
      const medico = this.getMedico(intervento)
      return medico && medico.telefono
    },
    showDettaglioIntervento (intervento) {
      this.$modal.show('intervento-prescrizioni-modal', { intervento })
    }
  }
}
</script>

<style scoped>
  .cell-row{display: block; margin: 3px 0; }
  @media only screen and (max-width: 768px) {
    dt, dd {
      font-size: 12px;
    }
  }
</style>
