<template>
    <div class='mobile-grey'>
        <div class='table-top'>
          <div class='title-with-actions'>
            <div class='table-info'>
              <div class='table-info-intro'><strong>Totale</strong> {{filteredPazienti.length}}</div>
            </div>
            <div class='toolbar-actions'>
              <div class='toolbar-btn'>
                <a :href='urlExcel()'>
                  <font-awesome-icon :icon="iconExcel" />
                  <span class='toolbar-btn-label'>Scarica in formato excel</span>
                </a>
              </div>
            </div>
          </div>
          <div class='toolbar'>
            <div class='toolbar-btn toolbar-btn-checkbox btn-filter-desktop' @click="onFilterOnlyTamponePositivoChange">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyTamponePositivo' />
              <!-- <div class='tampone-icon'>P</div> -->
              <font-awesome-icon :icon="iconTampone" />
              <span class='toolbar-btn-label'>Ultimo tampone positivo</span>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn toolbar-btn-checkbox btn-filter-desktop'  @click="onFilterOnlyQuarantenaChange">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyQuarantena'/>
                <font-awesome-icon :icon="iconHome" />
                <span class='toolbar-btn-label'>Quarantena</span>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn toolbar-btn-checkbox btn-filter-desktop'  @click="onFilterOnlyIsolamentoFiduciarioChange">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyIsolamentoFiduciario'/>
                <span class='toolbar-btn-label'>Isolamento fiduciario</span>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn toolbar-btn-checkbox btn-filter-desktop' @click="onFilterOnlyDomiciliatiChange">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyDomiciliati' />
              <!-- <div class='tampone-icon'>P</div> -->
              <span class='toolbar-btn-label'>Domiciliati</span>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn toolbar-btn-checkbox btn-filter-desktop' @click="onFilterOnlyResidentiChange">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyResidenti' />
              <!-- <div class='tampone-icon'>P</div> -->
              <span class='toolbar-btn-label'>Residenti</span>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn toolbar-btn-checkbox btn-filter-desktop' @click="onFilterMostraDecedutiChange">
              <input class="form-check-input" type="checkbox" v-model='filterMostraDeceduti' />
              <!-- <div class='tampone-icon'>P</div> -->
              <span class='toolbar-btn-label'>Includi deceduti</span>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn toolbar-btn-checkbox btn-filter-desktop' @click="onFilterOnlyAttualmentePositiviChange">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyAttualmentePositivi' />
              <!-- <div class='tampone-icon'>P</div> -->
              <span class='toolbar-btn-label'>Attualmente positivi</span>
            </div>
          </div>
        </div>
        <div class='toolbar pagination pagination-top'>
          <div class='mobile-filter'>
            <div class='toolbar-btn toolbar-btn-checkbox ' @click="onFilterOnlyTamponePositivoChange"
               v-bind:class="{ 'active': filterOnlyTamponePositivo}">
              <!-- <div class='tampone-icon'>P</div> -->
              <font-awesome-icon :icon="iconTampone" />
              <span> Ultimo tampone positivo</span>
            </div>
            <div class='toolbar-btn toolbar-btn-checkbox'  @click="onFilterOnlyQuarantenaChange"
              v-bind:class="{ 'active': filterOnlyQuarantena}">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyQuarantena' v-on:change="onFilterOnlyQuarantenaChange"/>
                <font-awesome-icon :icon="iconHome" />
                <span> Quarantena</span>
            </div>
            <div class='toolbar-btn toolbar-btn-checkbox'  @click="onFilterOnlyIsolamentoFiduciarioChange"
              v-bind:class="{ 'active': filterOnlyIsolamentoFiduciario}">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyIsolamentoFiduciario' v-on:change="onFilterOnlyIsolamentoFiduciarioChange"/>
                <font-awesome-icon :icon="iconHome" />
                <span> Isolamento fiduciario</span>
            </div>
            <div class='toolbar-btn toolbar-btn-checkbox'  @click="onFilterOnlyDomiciliatiChange"
              v-bind:class="{ 'active': filterOnlyDomiciliati}">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyDomiciliati' v-on:change="onFilterOnlyDomiciliatiChange"/>
              <span>Domiciliati</span>
            </div>
            <div class='toolbar-btn toolbar-btn-checkbox'  @click="onFilterOnlyResidentiChange"
              v-bind:class="{ 'active': filterOnlyResidenti}">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyResidenti' v-on:change="onFilterOnlyResidentiChange"/>
              <span>Residenti</span>
            </div>
            <div class='toolbar-btn toolbar-btn-checkbox'  @click="onFilterMostraDecedutiChange"
              v-bind:class="{ 'active': filterMostraDeceduti}">
              <input class="form-check-input" type="checkbox" v-model='filterMostraDeceduti' v-on:change="onFilterMostraDecedutiChange"/>
              <span>Includi deceduti</span>
            </div>
            <div class='toolbar-btn toolbar-btn-checkbox'  @click="onFilterOnlyAttualmentePositiviChange"
              v-bind:class="{ 'active': filterOnlyAttualmentePositivi}">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyAttualmentePositivi' v-on:change="onFilterOnlyAttualmentePositiviChange"/>
              <span>Attualmente positivi</span>
            </div>
          </div>
          <div class='mobile-pagination toolbar pagination pagination-bottom'>
            <div class='toolbar-btn'>
              <a heref @click="prevPage">
                <font-awesome-icon :icon="iconPrev" />
                <span class='toolbar-btn-label'>Precedente</span>
              </a>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
           <div>Pagina {{currentPage}} di {{Math.ceil((filteredPazienti?filteredPazienti.length:1) / pageSize)}}</div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn'>
              <a heref @click="nextPage">
                <font-awesome-icon :icon="iconNext" />
                <span class='toolbar-btn-label'>Prossima</span>
              </a>
            </div>
          </div>
        </div>
        <div v-if="loading" >
            <TheLoading />
        </div>
        <div v-else>
          <table class="table table-responsive1">
            <thead class="table-header">
              <tr>
                <th>&nbsp;</th>
                <th @click="sort('cognome')" ><p>Nome</p></th>
                <th @click="sort('comuneDomicilioIstat')" ><p>Domicilio</p></th>
                <th @click="sort('comuneResidenzaIstat')"><p>Residenza</p></th>
                <th @click="sort('telefonoRecapito')"><p>Telefono</p></th>
                <th @click="sort('tipoEvento')"><p>Dimissioni</p></th>
                <th><p>Data Dimissioni</p></th>
                <th><p>Data Fine Quarantena</p></th>
                <th title='Dato fornito da SISP'><p>Decorso Presso <span class='mute'><font-awesome-icon :icon="iconQuestion" /></span></p></th>
                <th><p>Esito tampone</p></th>
                <th title='Soggetto risultato positivo al test Covid-19 che non risulta in data odierna deceduto, guarito o in via di guarigione'><p>Attualmente positivo <span class='mute'><font-awesome-icon :icon="iconQuestion" /></span></p></th>
              </tr>
            </thead>
            <tbody >
              <!--<PazienteRow :paziente="p" />-->
              <tr class='table-row'  v-for="paziente in filteredPazientiAndPaginated" :key="paziente.idSoggetto"
                >
                <td class='mobile-row-cell'>
                  <div class='mobile-row'>
                    <div class='mobile-row-info'>
                      <div class='mobile-row-main'>
                          <div class='mobile-quarantena-icon'>
                            <div class='icon icon-isolamento-domiciliare' v-if='isolamentoDomiciliare(paziente)'>
                              <font-awesome-icon :icon="iconHome" />
                            </div>
                            <div class='icon icon-isolamento-domiciliare' v-if='assegnazioneDomicilio(paziente)'>
                              <font-awesome-icon :icon="iconBuilding" />
                            </div>
                          </div>
                          <div class='mobile-row-nome'> {{paziente.cognome + ' ' + paziente.nome}}</div>
                      </div>
                      <div class='mobile-row-dimissioni'>
                        <strong>Telefono</strong> <p><a :href='"tel:" +paziente.telefonoRecapito'>{{paziente.telefonoRecapito}}</a></p>
                      </div>
                      <div class='mobile-row-dimissioni'>
                        <strong>Tampone </strong>
                        <p v-bind:class="{ 'tampone-positivo': esitoTampone(paziente)==='Positivo'}">{{esitoTampone(paziente)}}</p>
                      </div>
                      <div class='mobile-row-dimissioni'>
                        <strong>Dimissioni</strong> {{(paziente.tipoEvento||{}).descTipoEvento|tipoEventoShort}}
                        <span v-if='paziente.decorso && paziente.decorso.dataDimissioni'> - {{(paziente.decorso||{}).dataDimissioni | formatMillis}}</span>
                      </div>
                      <div class='mobile-row-fine-quarantena' v-if='paziente.decorso && paziente.decorso.dataPrevFineEvento'>
                        <strong>Fine quarantena</strong> {{(paziente.decorso||{}).dataPrevFineEvento | formatMillis}}
                      </div>
                      <div class='mobile-row-dimissioni'>
                        <strong>Attualmente Positivo</strong> {{paziente.attualmentePositivo}}
                      </div>
                    </div>
                    <div class='mobile-expand-icon' @click="toggleMobileDetail(paziente.idSoggetto)">
                        <font-awesome-icon :icon="iconEllipsisV" />
                    </div>
                  </div>
                </td>
                <td class='no-mobile'>
                  <div class='icon icon-isolamento-domiciliare' v-if='isolamentoDomiciliare(paziente)'>
                    <font-awesome-icon :icon="iconHome" />
                  </div>
                  <div class='icon icon-isolamento-domiciliare' v-if='assegnazioneDomicilio(paziente)'>
                    <font-awesome-icon :icon="iconBuilding" />
                  </div>
                </td>
                <td class='mobile-detail-row'
                  v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                  <label class='no-desktop'>Nome</label><p>{{paziente.cognome + ' ' + paziente.nome }} </p>
                </td>
                <td class='mobile-detail-row'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <label class='no-desktop'>Domicilio</label><p>{{(paziente.comuneDomicilio||{}).nomeComune}}</p></td>
                <td class='mobile-detail-row'
                  v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                  <label class='no-desktop'>Residenza</label><p>{{(paziente.comuneResidenza||{}).nomeComune}}</p></td>
                <td class='mobile-detail-row'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                        <label class='no-desktop'>Telefono</label> <p><a :href='"tel:" +paziente.telefonoRecapito'>{{paziente.telefonoRecapito}}</a></p>
                <td class='mobile-detail-row'
                  v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                  <label class='no-desktop'>Dimissioni</label><p>{{(paziente.tipoEvento||{}).descTipoEvento}}</p></td>
                <td class='mobile-detail-row'
                  v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                  <label class='no-desktop'>Data Dimis.</label><p>{{(paziente.decorso||{}).dataDimissioni | formatMillis}}</p></td>
                <td class='mobile-detail-row'
                  v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                  <label class='no-desktop'>Fine Quarant.</label><p>{{(paziente.decorso||{}).dataPrevFineEvento | formatMillis}}</p></td>
                <td class='mobile-detail-row'
                  v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                  <label class='no-desktop'>Presso <i>Dato fornito da SISP</i></label>
                  <div>
                    <div v-if='isolamentoDomiciliare(paziente)' class='info-row'>
                        <small>{{(paziente.comuneRicovero||{}).nomeComune}} {{paziente.decorso.indirizzoDecorso}} ({{paziente.decorso.decorsoPresso}})</small>
                    </div>
                    <div v-if='assegnazioneDomicilio(paziente)' class='info-row'>
                        <small>{{paziente.struttura.nome}} - {{paziente.area.nome}}</small>
                    </div>
                    <div v-else-if='assegnatoArea(paziente)' class='info-row'>
                        <small>{{paziente.area.nome}}</small>
                    </div>
                  </div>
                </td>
                <td class='no-mobile'>
                  <p v-bind:class="{ 'tampone-positivo': esitoTampone(paziente)==='Positivo'}">{{esitoTampone(paziente)}}</p>
                </td>
                <td class='no-mobile'>
                  <p>{{paziente.attualmentePositivo}}</p>
                </td>
              </tr>
            </tbody>
          </table>
          <p>&nbsp;</p>
          <div class='toolbar pagination pagination-bottom'>
              <div class='toolbar-btn'>
                <a heref @click="prevPage">
                  <font-awesome-icon :icon="iconPrev" />
                  <span class='toolbar-btn-label'>Precedente</span>
                </a>
              </div>
              <div class='toolbar-separator'>&nbsp;</div>
              Pagina {{currentPage}} di {{Math.ceil((filteredPazienti?filteredPazienti.length:1) / pageSize)}}
              <div class='toolbar-separator'>&nbsp;</div>
              <div class='toolbar-btn'>
                <a heref @click="nextPage">
                  <font-awesome-icon :icon="iconNext" />
                  <span class='toolbar-btn-label'>Prossima</span>
                </a>
              </div>
<!--
            <div class='toolbar-btn'>
              <button  @click="prevPage">Precedente</button>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn'>
              <button class='toolbar-btn toolbar-btn-div' @click="nextPage">Prossima</button>
            </div> -->
          </div>
        </div>
    </div>
</template>

<script>
import TheLoading from '@/components/common/TheLoading'
// import PazienteRow from '@/components/paziente/PazienteRow'
import Constants from '@/util/constants'
import { faFileExcel, faFilter, faHome, faBuilding, faAngleRight, faArrowCircleLeft, faArrowCircleRight, faVial, faEllipsisV, faQuestionCircle } from '@fortawesome/free-solid-svg-icons'

export default {
  name: 'Pazienti',
  components: {
    TheLoading
  },
  data () {
    return {
      iconFilter: faFilter,
      iconExcel: faFileExcel,
      iconHome: faHome,
      iconTampone: faVial,
      iconBuilding: faBuilding,
      iconArrowRight: faAngleRight,
      iconNext: faArrowCircleRight,
      iconPrev: faArrowCircleLeft,
      iconEllipsisV: faEllipsisV,
      iconQuestion: faQuestionCircle,
      currentUser: null,

      urlExcel: function () { return Constants.VISURAPAZIENTI_BASE_URL + '/visura/soggetti/report/xlsx' },
      pazienti: null,
      loading: true,
      errored: false,
      filterOnlyTamponePositivo: false,
      filterOnlyQuarantena: false,
      filterOnlyIsolamentoFiduciario: false,
      filterOnlyDomiciliati: false,
      filterOnlyResidenti: false,
      filterMostraDeceduti: false,
      filterOnlyAttualmentePositivi: false,
      currentSort: 'cognome',
      currentSortDir: 'asc',
      pageSize: 10,
      currentPage: 1,
      mobileDetailOpen: {},
      comuneDomicilio: function (paziente) {
        return paziente.comuneDomicilio
      },
      comuneResidenza: function (paziente) {
        return paziente.comuneResidenza
      },
      isolamentoDomiciliare: function (paziente) {
        return paziente.decorso &&
           (paziente.decorso.idTipoEvento === Constants.ID_ISOLAMENTO_DOMICILIARE ||
            paziente.decorso.idTipoEvento === Constants.ID_DISPOSTA_QUARANTENA_DOMIC
           )
      },
      assegnazioneDomicilio: function (paziente) {
        return paziente.decorso &&
           (paziente.decorso.idTipoEvento === Constants.ID_ASSEGNAZIONE_DOMICILIO ||
            paziente.decorso.idTipoEvento === Constants.ID_DISPOSTA_QUARANTENA_EXTRA_DOMIC
           )
      },
      assegnatoArea: function (paziente) {
        return paziente.area && paziente.area.nome
      },
      esitoTampone: function (paziente) {
        return paziente.tampone && paziente.tampone.idRisTamp ? Constants.ESITO_TAMPONE[paziente.tampone.idRisTamp] : ''
      }
    }
  },
  methods: {
    nextPage: function () {
      console.log(this.currentPage)
      if ((this.currentPage * this.pageSize) < this.filteredPazienti.length) this.currentPage++
    },
    prevPage: function () {
      if (this.currentPage > 1) this.currentPage--
    },
    sort: function (s) {
      if (s === this.currentSort) {
        this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc'
      }
      this.currentSort = s
    },
    onFilterOnlyTamponePositivoChange: function () {
      this.filterOnlyTamponePositivo = !this.filterOnlyTamponePositivo
      this.currentPage = 1
    },
    onFilterOnlyQuarantenaChange: function () {
      this.filterOnlyQuarantena = !this.filterOnlyQuarantena
      this.currentPage = 1
    },
    onFilterOnlyIsolamentoFiduciarioChange: function () {
      this.filterOnlyIsolamentoFiduciario = !this.filterOnlyIsolamentoFiduciario
      this.currentPage = 1
    },
    onFilterOnlyDomiciliatiChange: function () {
      this.filterOnlyDomiciliati = !this.filterOnlyDomiciliati
      this.currentPage = 1
    },
    onFilterOnlyResidentiChange: function () {
      this.filterOnlyResidenti = !this.filterOnlyResidenti
      this.currentPage = 1
    },
    onFilterMostraDecedutiChange: function () {
      this.filterMostraDeceduti = !this.filterMostraDeceduti
      this.currentPage = 1
    },
    onFilterOnlyAttualmentePositiviChange: function () {
      this.filterOnlyAttualmentePositivi = !this.filterOnlyAttualmentePositivi
      this.currentPage = 1
    },
    filtriCheck: function (paziente) {
      return ((!this.filterOnlyTamponePositivo) || (paziente.tampone && paziente.tampone.idRisTamp === 2)) &&
      ((!this.filterOnlyQuarantena) ||
        (paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_ISOLAMENTO_DOMICILIARE) ||
        (paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_ASSEGNAZIONE_DOMICILIO) ||
        (paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_DISPOSTA_QUARANTENA_DOMIC) ||
        (paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_DISPOSTA_QUARANTENA_EXTRA_DOMIC)
      ) &&
      ((!this.filterOnlyIsolamentoFiduciario) ||
        (paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_ISOLAMENTO_FIDUCIARIO)
      ) &&
      ((this.filterMostraDeceduti) ||
        (!paziente.decorso || paziente.decorso.idTipoEvento !== Constants.ID_DECEDUTO)
      ) &&
      (!this.filterOnlyResidenti ||
        (paziente.comuneResidenzaIstat === this.currentUser.sindaco.comune.istatComune)
      ) &&
      (!this.filterOnlyDomiciliati ||
        (paziente.comuneDomicilioIstat === this.currentUser.sindaco.comune.istatComune)
      ) &&
      (!this.filterOnlyAttualmentePositivi ||
        (paziente.attualmentePositivo === 'SI')
      )
    },
    numPazientiEsitoPositivo: function () {
      return this.pazienti.filter((obj) => (obj.tampone && obj.tampone.idRisTamp === 2)).length
    },
    numPazientiQuarantena: function () {
      return this.pazienti.filter((obj) => (
        (obj.decorso && obj.decorso.idTipoEvento === Constants.ID_ISOLAMENTO_DOMICILIARE) ||
        (obj.decorso && obj.decorso.idTipoEvento === Constants.ID_ASSEGNAZIONE_DOMICILIO) ||
        (obj.decorso && obj.decorso.idTipoEvento === Constants.ID_DISPOSTA_QUARANTENA_DOMIC) ||
        (obj.decorso && obj.decorso.idTipoEvento === Constants.ID_DISPOSTA_QUARANTENA_EXTRA_DOMIC)
      )).length
    },
    numPazientiPositiviResidenti: function () {
      return this.pazienti.filter((obj) => (obj.tampone && obj.tampone.idRisTamp === 2 &&
        obj.comuneResidenzaIstat && obj.comuneResidenzaIstat === this.currentUser.sindaco.comuneIstat)).length
    },
    numPazientiPositiviDomicilio: function () {
      return this.pazienti.filter((obj) => (obj.tampone && obj.tampone.idRisTamp === 2 &&
        obj.comuneDomicilioIstat && obj.comuneDomicilioIstat === this.currentUser.sindaco.comuneIstat)).length
    },
    numPazientiPositiviQuarantena: function () {
      return this.pazienti.filter((obj) => (obj.tampone && obj.tampone.idRisTamp === 2 &&
        (
          (obj.decorso && obj.decorso.idTipoEvento === Constants.ID_ISOLAMENTO_DOMICILIARE) ||
          (obj.decorso && obj.decorso.idTipoEvento === Constants.ID_ASSEGNAZIONE_DOMICILIO) ||
          (obj.decorso && obj.decorso.idTipoEvento === Constants.ID_DISPOSTA_QUARANTENA_DOMIC) ||
          (obj.decorso && obj.decorso.idTipoEvento === Constants.ID_DISPOSTA_QUARANTENA_EXTRA_DOMIC)
        ))).length
    },

    toggleMobileDetail: function (idSoggetto) {
      console.log('tt', this.mobileDetailOpen)
      if (!this.mobileDetailOpen['s_' + idSoggetto]) {
        this.$set(this.mobileDetailOpen, 's_' + idSoggetto, true)
      } else {
        this.$set(this.mobileDetailOpen, 's_' + idSoggetto, !this.mobileDetailOpen['s_' + idSoggetto])
      }
    },
    showMobileDetail: function (idSoggetto) {
      return this.mobileDetailOpen['s_' + idSoggetto] === true
    }

  },
  computed: {
    filteredPazienti: function () {
      if (this.pazienti) {
        return this.pazienti.filter(obj => this.filtriCheck(obj)).sort((a, b) => {
          let modifier = 1
          if (this.currentSortDir === 'desc') modifier = -1
          if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier
          if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier
          return 0
        })
      } else {
        console.log('this.pazienti null')
        return this.pazienti
      }
    },
    filteredPazientiAndPaginated: function () {
      if (this.filteredPazienti) {
        return this.filteredPazienti.filter((row, index) => {
          var start = (this.currentPage - 1) * this.pageSize
          var end = this.currentPage * this.pageSize
          if (index >= start && index < end) return true
        })
      } else {
        console.log('this.pazienti null')
        return this.filteredPazienti
      }
    }
  },
  mounted () {
    this.$http
      .get(Constants.VISURAPAZIENTI_BASE_URL + '/visura/soggetti')
      .then(response => {
        this.currentUser = this.$store.getters.currentUser
        this.pazienti = response.data
        console.log('getPazienti', response.data)
      })
      .catch(error => {
        console.log(error)
        this.errored = true
      })
      .finally(this.loading = false)
  }
}
</script>

<style scoped>
  .table-top{margin-bottom: 2rem}
  .toolbar-btn.toolbar-btn-checkbox{padding: .5em .6em .5em .2em}
  .toolbar-actions{display: flex;padding: 4px 8px;justify-content: flex-end;}
  .title-with-actions{display: flex;padding: 4px 8px;align-items: center;margin-bottom: .5rem; align-content: center;}
  .toolbar{flex-grow: 1;margin: 0; text-align: left; justify-content: flex-end;padding: 4px 16px;}
  .toolbar-icon{margin: 0 12px;}
  .table-row{cursor: pointer;}
  .pagination{justify-content: center;}
  .table-info{flex-grow: 1; display: flex; }
  .table-info-separator{border-left: solid 1px #ccc; margin-left:.5em; padding-left: .5em;}
  .table-info-intro{margin-right: 1em; font-size: 1em;}
  .table-info-data{display: flex;flex-direction: column; font-size: .8em; white-space: nowrap;}
  .table .table-header {text-align: left;}
  .table-info-items{display: flex;}
  .pagination-top{display: none;}
  a{text-decoration: none;}

  .mobile-row-cell{display: none;}
  .no-desktop{display: none;}
  .mute{color: #bbb;}

  @media only screen and (max-width: 768px) {
     .table-top{background: #fff; margin-bottom: 0;}
     .title-with-options{margin-bottom:0;padding-bottom: 1rem;text-align: left;align-items: flex-start;background: #fff;}
     .table-info{ width: 100%; }
     .table-info-items{flex-direction: column;font-size: .8em; text-align: left;}
     .toolbar{flex-direction: column; background: #fff; justify-content: space-between;}
     .table-info-separator{display: none;}
     .pagination-top{display: block;   padding: 0 8px 1rem 8px;box-shadow:   0 8px 6px -6px rgba(0,0,0,.3);margin-bottom: 8px;}
     .mobile-pagination{display: flex;}
     .pagination-top .mobile-pagination{padding: 0px 3px;}
     .pagination-bottom.toolbar{flex-direction: row;}
     .pagination-top.toolbar{flex-direction: column;}
     .toolbar-separator-grow{flex-grow: 1;}
     .btn-filter-desktop{display: none;}
     .mobile-white{background: #fff;}
     .mobile-filter{display: flex;flex-wrap: wrap;margin-bottom: 12px; justify-content: space-between;}
     .mobile-filter input{display: none;}
     .mobile-filter .toolbar-btn-checkbox{width: auto; margin: 0 3px 12px 3px; font-size: 14px;}
     .mobile-filter .toolbar-btn-checkbox.active{background-color:#006cb4; color: white; border-color: #005994}

    .table .table-header {display: none;}
    .mobile-grey{background: #f0f3f8;}
    .table{background: #f0f3f8;}
    .table .table-row {box-shadow: 0 1px 8px rgba(0,0,0,.2), 0 3px 4px rgba(0,0,0,.14), 0 3px 3px -2px rgba(0,0,0,.12);
      margin: 1rem; display: block;}
    .table .table-row p { border: 0; display: inline; padding: 0;}
    .table .table-row td { display: block; position: relative;padding: 4px 8px;background: #fff; }
    .table1 .table-row td:last-child { border-bottom: 0px solid #ccc;}
    .table1 .table-row td:before { content: ''; background: #ddd; position: absolute; top: 0; bottom: 0; left: 0; width: 6.5rem;}
    .table1 .table-row td:after {content: attr(data-th);position: absolute;
      top: 0;left: 0; display: inline-block; padding-left: 10px;max-width: 6rem;}

    .no-desktop{display: block;}
    .table td{border: none}
    .table .table-row td.no-mobile{display: none;}
    .mobile-row-cell{display: block; height: auto;}
    .mobile-row{display: flex; align-content: center;}
    .mobile-row-info{flex-grow: 1; padding: 8px 0;}
    .mobile-row-main{display: flex; align-content: top;margin-bottom: 8px;}
    .mobile-row-nome{font-weight: bold; font-size: 1.2em;flex-grow: 1;}
    .mobile-row-dimissioni{font-size: .8em; margin-bottom: 2px;}
    .mobile-row-fine-quarantena{font-size: .8em}
    .mobile-quarantena-icon{font-size:1rem;text-align: center; margin-right:.6rem;}
    .mobile-expand-icon{font-size: 1rem;text-align: center;padding: 8px 1rem;}

    .mobile-detail-row{font-size: .8em;}
    label.no-desktop{font-weight: bold; display: inline-block;padding-right: .5em;}
    label.no-desktop i{font-weight: normal;padding-left: 1em;font-size: smaller;}
    .table .table-row td.mobile-detail-hide{display: none;}
    .table .table-row td.mobile-detail-show{display: block;}
   }
</style>
