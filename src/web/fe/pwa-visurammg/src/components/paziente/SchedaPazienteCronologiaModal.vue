<template>
  <modal
    name="scheda-paziente-cronologia"
    :adaptive='true'
    height="auto"
    :scrollable="true"
    :styles="'display: flex;padding: 12px;background-color: transparent;box-shadow: none' "
    @before-open="beforeOpen"
  >
    <div class='modal-content'>
      <div class="close" @click="$modal.hide('scheda-paziente-cronologia')">
        &times;
      </div>

      <div class='modal-title pa-md'>Cronologia richiesta di intervento</div>

      <template v-if='scheda'>
        <div class="pa-md py-lg">
          <table class="table" width="100%">
            <thead class="table-header">
            <tr>
              <th>Data</th>
              <th>Stato</th>
              <th>Operatore</th>
              <th>Telefono</th>
<!--              <th>Email</th>-->
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in cronologia" :key="item.id" class="table-row">
              <td>{{item.data_inizio|formatMillis}}</td>
              <td>{{(item.stato||{}).nome|empty}}</td>
              <td>
                <template v-if="item.operatore && (item.operatore.nome || item.operatore.cognome)">
                  {{(item.operatore||{}).cognome}} {{(item.operatore||{}).nome}}
                </template>
                <template v-else>
                  -
                </template>
              </td>
              <td>
                <a :href="'tel:' + (item.operatore||{}).telefono || ''" class="no-decoration">
                  {{(item.operatore||{}).telefono | empty}}
                </a>
              </td>
<!--              <td>-->
<!--                <a :href="'mailto:' + (item.operatore||{}).mail || ''" class="no-decoration">-->
<!--                  {{(item.operatore||{}).mail | empty}}-->
<!--                </a>-->
<!--              </td>-->
            </tr>
            </tbody>
          </table>
        </div>
      </template>

      <div class='modal-footer pa-md'>
        <div class='toolbar-right'>
          <a href @click.prevent="$modal.hide('scheda-paziente-cronologia')" class='btn btn-default'>Chiudi</a>
        </div>
      </div>
    </div>
  </modal>
</template>

<script>
export default {
  name: 'SchedaPazienteCronologiaModal',
  data () {
    return {
      scheda: null
    }
  },
  computed: {
    cronologia () {
      return (this.scheda && this.scheda.cronologia) || []
    }
  },
  methods: {
    beforeOpen (event) {
      console.log('beforeOpen', event)
      this.scheda = event.params.scheda
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
</style>
