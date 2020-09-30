<template>
    <div class='lista-detail-container'  v-if="paziente">
        <div class='tab-buttons'>
            <div class='tab-button' v-bind:class="{ 'active': activeTab ==='Decorsi'}" @click="chooseTab('Decorsi')">Decorsi</div>
            <div class='tab-button' v-bind:class="{ 'active': activeTab ==='Tamponi'}" @click="chooseTab('Tamponi')">Tamponi</div>
            <div class='tab-button' v-bind:class="{ 'active': activeTab ==='Schede'}" @click="chooseTab('Schede')">Interventi USCA</div>
            <div class='tab-button' v-bind:class="{ 'active': activeTab ==='Diari'}" @click="chooseTab('Diari')">Diario</div>
            <div class=' tab-button-empty'>&nbsp;</div>
        </div>
        <div class='tab-content ' v-bind:class="{ 'active': activeTab ==='Decorsi'}" >
            <table class='table-small' v-if="paziente.elencoDecorso && paziente.elencoDecorso.length>0">
                <thead>
                    <tr>
                    <th>Data</th>
                    <th>Evento</th>
                    <th>Fine quarantena</th>
                    <th>Condizioni cliniche</th>
                    <th>Data esordio sintomi malattia</th>
                    <th>Note</th>
                    <th>Sintomi</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="d in paziente.elencoDecorso" :key="d.idDecorso">
                    <td>{{d.dataDimissioni| formatMillis}}</td>
                    <td>{{(d.decodeTipoEvento||{}).descTipoEvento}}</td>
                    <td>{{d.dataPrevFineEvento|formatMillis}}</td>
                    <td>{{d.condizioniCliniche}}</td>
                    <td>{{d.dataInizioSint|formatMillis}}</td>
                    <td>{{d.note}}</td>
                    <td>
                        <span v-if='!d.sintomo'>{{d.sintomi}}</span>
                        <span v-if='d.sintomo' class='btn sintomo-button' @click="showDettaglioSintomo(d)">{{d.sintomi}}</span>
                    </td>
                    </tr>
                </tbody>
            </table>
            <div v-else class='nowrap'> Non sono presenti decorsi</div>
        </div>
        <div class='tab-content' v-bind:class="{ 'active': activeTab ==='Tamponi'}" >
            <table  class='table-small' v-if="paziente.elencoTampone && paziente.elencoTampone.length>0">
                <thead>
                    <tr>
                    <th>Data richiesta</th>
                    <th>Criterio epid.</th>
                    <th>Tipologia test covid</th>
                    <th>Esito tampone</th>
                    <th>Data test</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="t in paziente.elencoTampone" :key="t.idTampone">
                    <td>{{t.dataInserimentoRichiesta|formatMillis}}</td>
                    <td>{{t.criterioEpidemiologicoCovid19}}</td>
                    <td>{{(t.testCovid||{}).testCovid}}</td>
                    <td>{{(t.risTampone||{}).risultatoTampone}}</td>
                    <td>{{t.dataTest|formatMillis}}</td>
                    </tr>
                </tbody>
            </table>
            <div v-else class='nowrap'>Non sono presenti richieste di tampone</div>
        </div>
        <div class='tab-content' v-bind:class="{ 'active': activeTab ==='Schede'}" >
            <table  class='table-small' v-if="schede && schede.length>0">
                <thead>
                    <tr>
                        <th>Richiedente</th>
                        <th>Data richiesta</th>
                        <th>Stato richiesta</th>
                        <th>Destinatario</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="s in schedeOrdinate" :key="s.id">
                        <td>
                            <span class='cell-row'>{{(s.medico||{}).nome}} {{(s.medico||{}).cognome}}</span>
                            <span class='cell-row'>Telefono: {{(s.medico||{}).telefono}}</span>
                        </td>
                        <td>
                          <span class='cell-row'>{{s.data|formatDateTime}}</span>
<!--                          <template v-if="s.critica">-->
<!--                            <span class='cell-row text-danger'>-->
<!--                              <strong>Urgente</strong>-->
<!--                            </span>-->
<!--                          </template>-->
                        </td>
                        <td>
                          <span class='cell-row'>{{getLabelStatoScheda(s.stato) | empty}}</span>
                          <template v-if="(s.stato||{}).codice === SCHEDA_STATI.ACCETTATA">
                            <span class='cell-row'>
                              da {{getNomeAutoreCambioStato(s, SCHEDA_STATI.ACCETTATA) | empty}}
                            </span>
                            <template v-if="getTelefonoAutoreCambioStato(s, SCHEDA_STATI.ACCETTATA)">
                              <span class="cell-row">
                                <a :href="'tel:' + getTelefonoAutoreCambioStato(s, SCHEDA_STATI.ACCETTATA)" class="no-decoration">
                                  {{getTelefonoAutoreCambioStato(s, SCHEDA_STATI.ACCETTATA)}}
                                </a>
                              </span>
                            </template>
                          </template>
                          <template v-if="(s.stato||{}).codice === SCHEDA_STATI.EVASA">
                            <span class='cell-row'>
                              da {{getNomeAutoreCambioStato(s, SCHEDA_STATI.EVASA) | empty}}
                            </span>
                            <template v-if="getTelefonoAutoreCambioStato(s, SCHEDA_STATI.EVASA)">
                              <span class="cell-row">
                                <a :href="'tel:' + getTelefonoAutoreCambioStato(s, SCHEDA_STATI.EVASA)" class="no-decoration">
                                  {{getTelefonoAutoreCambioStato(s, SCHEDA_STATI.EVASA)}}
                                </a>
                              </span>
                            </template>
                          </template>
                          <template v-if="(s.stato||{}).codice === SCHEDA_STATI.RIFIUTATA">
                            <span class='cell-row'>
                              da {{getNomeAutoreCambioStato(s, SCHEDA_STATI.RIFIUTATA) | empty}}
                            </span>
                            <template v-if="getTelefonoAutoreCambioStato(s, SCHEDA_STATI.RIFIUTATA)">
                              <span class="cell-row">
                                <a :href="'tel:' + getTelefonoAutoreCambioStato(s, SCHEDA_STATI.RIFIUTATA)" class="no-decoration">
                                  {{getTelefonoAutoreCambioStato(s, SCHEDA_STATI.RIFIUTATA)}}
                                </a>
                              </span>
                            </template>
                          </template>
                        </td>
                        <td>{{(s.usca||{}).nome|empty}}</td>
                        <td class='column-slim '>
                                <span  class='btn cell-row' @click="showDettaglioScheda(s)">
                                    dettaglio
                                </span>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div v-else class='nowrap'>Non sono presenti schede</div>
        </div>
        <div class='tab-content' v-bind:class="{ 'active': activeTab ==='Diari'}" >

            <div class="mb-md text-right">
              <a href  @click.prevent="showNuovaPaginaDiario" class='btn btn-default'>Aggiungi</a>
            </div>

            <table  class='table-small' v-if="pagineDiario.length > 0">
                <thead>
                    <tr>
                    <th>Data</th>
                    <th>Autore</th>
                    <th>Sintomi</th>
                    <th>Altri sintomi</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="d in pagineDiarioOrdinate" :key="d.id">
                        <td class='column-slim nowrap'>{{d.data|formatDateTime}}</td>
                        <td>
                          <template v-if="!d.autore">
                            -
                          </template>
                          <template v-else>
                            {{d.autore.cognome}} {{d.autore.nome}}
                          </template>
                        </td>
                        <td style="max-width: 300px">
                          <sintomi-label :sintomi="d.sintomi" />
                        </td>
                        <td>{{d.note}}</td>
                    </tr>
                </tbody>
            </table>
            <div v-else class='nowrap'>Non sono presenti diari</div>
        </div>

    </div>
</template>
<script>
import Constants from '@/util/constants'
import SintomiLabel from './SintomiLabel'
import { getAutoreCambioStato } from '../../util/business-logic'

export default {
  name: 'PazienteDetailRow',
  components: { SintomiLabel },
  props: ['paziente'],
  data () {
    return {
      SCHEDA_STATI: Constants.SCHEDA_STATI,
      schede: null,
      diari: null,
      errored: false,
      activeTab: 'Decorsi'
    }
  },
  computed: {
    diario () {
      const diari = this.diari || []
      return diari[0] || {}
    },
    pagineDiario () {
      return this.diario.dettagli || []
    },
    pagineDiarioOrdinate () {
      const result = [...this.pagineDiario]

      result.sort((a, b) => {
        return new Date(a.data) - new Date(b.data) > 0 ? -1 : 1
      })

      return result
    },
    schedeOrdinate () {
      const result = [...this.schede]

      result.sort((a, b) => {
        return new Date(a.data) - new Date(b.data) > 0 ? -1 : 1
      })

      return result
    }
  },
  methods: {
    showDettaglioSintomo: function (d) {
      this.$modal.show('sintomo-detail', { decorso: d })
    },
    showDettaglioPrescrizione: function (p) {
      this.$modal.show('prescrizione-detail', { prescrizione: p })
    },
    showDettaglioIntervento: function (intervento) {
      this.$modal.show('intervento-detail', { intervento: intervento })
    },
    showCronologia (scheda) {
      this.$modal.show('scheda-paziente-cronologia', { scheda })
    },
    showDettaglioScheda (scheda) {
      this.$modal.show('scheda-detail', { scheda })
    },
    showNuovaPaginaDiario () {
      this.$modal.show('diario-pagina-nuova', { soggetto: this.paziente })
    },
    chooseTab: function (activeTab) {
      this.activeTab = activeTab
      if (activeTab === 'Schede' && !this.schede) {
        console.log('getSchede')
        this.$http.get(Constants.VISURAMMG_EXTERNAL_URL + 'schede?soggetto=' + this.paziente.idSoggetto).then(response => {
          this.schede = response.data
          console.log('getSchede', response.data)
        }).catch(error => {
          console.log(error)
        }).then(this.loading = false)
      } else if (activeTab === 'Diari' && !this.diari) {
        console.log('getDiari')
        this.$http.get(Constants.VISURAMMG_EXTERNAL_URL + 'diari?soggetto=' + this.paziente.idSoggetto).then(response => {
          this.diari = response.data
          console.log('getDiari', response.data)
        }).catch(error => {
          console.log(error)
        }).then(this.loading = false)
      }
      // this.$set(this.activeTab, activeTab, true)
    },
    getNomeAutoreCambioStato (scheda, stato) {
      const utente = getAutoreCambioStato(scheda, stato)
      const nome = utente && utente.nome
      const cognome = utente && utente.cognome
      return [cognome, nome].join(' ').trim()
    },
    getTelefonoAutoreCambioStato (scheda, stato) {
      const utente = getAutoreCambioStato(scheda, stato)
      return utente && utente.telefono
    },
    getLabelStatoScheda (stato) {
      let result = Constants.SCHEDA_STATI_LABEL[stato.codice]
      if (result) result = result.toUpperCase()
      return result
    }
  }
}
</script>

<style scoped>
    .hide{display: none;}
    .lista-detail-container{ margin: 15px;text-align: left; background-color: white; box-shadow:   0 8px 6px -6px rgba(0,0,0,.3);}
    .tab-buttons{display: flex;font-size: 14px; line-height: 36px; border-bottom: solid 1px #ddd; width: 100%; justify-content: left;}
    .tab-button{padding: 5px 15px 3px 15px; font-weight: bold; border-bottom: solid 3px transparent; color: #9d9d9d;}
    .tab-button-empty{flex-grow: 1;padding: 5px 15px 3px 15px; font-weight: bold; border-bottom: solid 3px transparent;}
    .tab-button:hover{color: #e30613; border-color:  #e30613; cursor: pointer;}
    .tab-button.active{color: #e30613; border-color:  #e30613; cursor: pointer;}
    .tab-content{padding: 15px; width: 100%; display: none; box-sizing: border-box;background-color: #fafafa;}
    .tab-content.active{display: block;}
    .table-small{border-collapse: collapse;border-spacing: 0;width: 100%;}
    .table-small th{padding: 0px 12px;}
    .table-small td{ padding: 6px 12px; border-bottom: solid 1px #ddd; }
    .sintomo-button{padding: .1em 1em;}
    .btn.cell-row{text-align: center;}
    .cell-row{display: block; margin: 3px 0; }
    .column-slim{vertical-align: top;}
    .column-slim .btn{white-space: nowrap;}
    td{vertical-align: top;}
    @media only screen and (max-width: 768px) {
    }
</style>
