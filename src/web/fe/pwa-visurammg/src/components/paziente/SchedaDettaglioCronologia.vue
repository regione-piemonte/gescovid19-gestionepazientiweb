<template>
  <div class='scheda-dettaglio-cronologia'>
    <table class="table" width="100%">
      <thead class="table-header">
      <tr>
        <th>Data</th>
        <th>Stato</th>
        <th>Operatore</th>
        <th class='no-mobile'>Telefono</th>
<!--        <th class='no-mobile'>Email</th>-->
      </tr>
      </thead>

      <tbody>
      <tr v-for="item in cronologia" :key="item.data_inizio" class="table-row">
        <td>{{item.data_inizio|formatDateTime}}</td>
        <td>{{getLabelStatoScheda(item.stato) | empty}}</td>
        <td>
          <template v-if="item.utente && (item.utente.nome || item.utente.cognome)">
            {{(item.utente||{}).cognome}} {{(item.utente||{}).nome}}
            <div class='no-desktop'>
              <br>
              <a :href="'tel:' + (item.utente||{}).telefono || ''" class="no-decoration">
                {{(item.utente||{}).telefono | empty}}
              </a>
              <br>
<!--              <a :href="'mailto:' + (item.utente||{}).email || ''" class="no-decoration">-->
<!--                 {{(item.utente||{}).email | empty}}-->
<!--              </a>-->
          </div>
          </template>
          <template v-else>
            -
          </template>
        </td>
        <td  class='no-mobile'>
          <a :href="'tel:' + (item.utente||{}).telefono || ''" class="no-decoration">
            {{(item.utente||{}).telefono | empty}}
          </a>
        </td>
<!--        <td  class='no-mobile'>-->
<!--          <a :href="'mailto:' + (item.utente||{}).email || ''" class="no-decoration">-->
<!--            {{(item.utente||{}).email | empty}}-->
<!--          </a>-->
<!--        </td>-->
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import constants from '../../util/constants'

export default {
  name: 'SchedaDettaglioCronologia',
  props: {
    scheda: { type: Object, required: false, default: () => null }
  },
  computed: {
    cronologia () {
      return (this.scheda && this.scheda.cronologia) || {}
    }
  },
  methods: {
    getLabelStatoScheda (stato) {
      let result = constants.SCHEDA_STATI_LABEL[stato.codice]
      if (result) result = result.toUpperCase()
      return result
    }
  }
}
</script>

<style scoped>
  .no-desktop{display: none;}
  @media only screen and (max-width: 768px) {
    .no-mobile{display: none;}
    .scheda-dettaglio-cronologia{font-size: .9em;}
  }
</style>
