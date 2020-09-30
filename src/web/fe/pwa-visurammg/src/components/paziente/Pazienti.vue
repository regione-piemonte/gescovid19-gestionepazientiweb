<template>
    <div class='mobile-grey'>
      <!-- TITOLO -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
        <div class='title-with-options'>
          <div class='table-info'>
            <div class='table-info-intro'><strong>Pazienti</strong></div>
            <div class='table-info-items'>
              <div class='table-info-item'>totali: <strong v-if='pazienti'>{{pazienti.length}}</strong></div>
              <div class='table-info-separator'>&nbsp;</div>
              <div class='table-info-item'>con tampone positivo: <strong v-if='pazienti'>{{numPazientiEsitoPositivo()}}</strong></div>
              <div class='table-info-separator'>&nbsp;</div>
              <div class='table-info-item'>in quarantena: <strong v-if='pazienti'>{{numPazientiQuarantena()}}</strong></div>
            </div>
          </div>

          <!-- AZIONI -->
          <!-- ----------------------------------------------------------------------------------------------------- -->
          <div class='toolbar'>
            <!-- <div class='toolbar-btn'>
              <a href>
                <font-awesome-icon :icon="iconFilter" />
                <span class='toolbar-btn-label'>Filtri</span>
              </a>
            </div> -->
            <div class='toolbar-btn toolbar-btn-checkbox btn-filter-desktop' @click="onFilterOnlyTamponePositivoChange">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyTamponePositivo' />
              <!-- <div class='tampone-icon'>P</div> -->
              <font-awesome-icon :icon="iconTampone" />
              <span class='toolbar-btn-label'>Tampone positivo</span>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn toolbar-btn-checkbox btn-filter-desktop'  @click="onFilterOnlyQuarantenaChange">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyQuarantena'/>
                <font-awesome-icon :icon="iconHome" />
                <span class='toolbar-btn-label'>Quarantena</span>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn'>
              <a :href='urlExcel()'>
                <font-awesome-icon :icon="iconExcel" />
                <span class='toolbar-btn-label'>Scarica in formato excel</span>
              </a>
            </div>
            <div class='toolbar-separator toolbar-separator-vertical-mobile'>&nbsp;</div>
            <div class='toolbar-btn' @click="showAggiungiSegnalazione(null, true)">
                <font-awesome-icon :icon="iconAdd" />
                <span class='toolbar-btn-label'>
                  Nuovo  paziente
                  con segnalazione
                </span>
            </div>
            <div class='toolbar-separator toolbar-separator-vertical-mobile'>&nbsp;</div>
            <div class='toolbar-btn' @click="showAggiungiSegnalazione()">
              <font-awesome-icon :icon="iconAdd" />
              <span class='toolbar-btn-label'>
                Nuovo  paziente
                senza segnalazione
              </span>
            </div>
          </div>
        </div>

        <div class='toolbar pagination pagination-top'>
            <div class='toolbar-btn toolbar-btn-checkbox ' @click="onFilterOnlyTamponePositivoChange">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyTamponePositivo' v-on:change="onFilterOnlyTamponePositivoChange"/>
              <!-- <div class='tampone-icon'>P</div> -->
              <font-awesome-icon :icon="iconTampone" />
              <span class='toolbar-btn-label'>Tampone positivo</span>
            </div>
            <div class='toolbar-separator'>&nbsp;</div>
            <div class='toolbar-btn toolbar-btn-checkbox'  @click="onFilterOnlyQuarantenaChange">
              <input class="form-check-input" type="checkbox" v-model='filterOnlyQuarantena' v-on:change="onFilterOnlyQuarantenaChange"/>
                <font-awesome-icon :icon="iconHome" />
                <span class='toolbar-btn-label'>Quarantena</span>
            </div>
          <div class='toolbar-separator-grow'>&nbsp;</div>
          <div class='toolbar-btn'>
            <a heref @click="prevPage">
              <font-awesome-icon :icon="iconPrev" />
              <span class='toolbar-btn-label'>Precedente</span>
            </a>
          </div>
          <div class='toolbar-separator'>&nbsp;</div>
          {{currentPage}}/{{Math.ceil((filteredPazienti?filteredPazienti.length:1) / pageSize)}}
          <div class='toolbar-separator'>&nbsp;</div>
          <div class='toolbar-btn'>
            <a heref @click="nextPage">
              <font-awesome-icon :icon="iconNext" />
              <span class='toolbar-btn-label'>Prossima</span>
            </a>
          </div>
        </div>

      <!-- RICERCA E FILTRI USCA -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <div class="py-md row items-center justify-between">
        <div class="col-mobile-12 py-md">
          <div class="pa-md row items-center">
            <div class="col-mobile-12 text-bold text-left">
              Filtra per interventi USCA:
            </div>

            <div class='btn mx-md' @click="filtraPerStatoScheda(SCHEDA_STATI.INVIATA)">
              <font-awesome-icon :icon="iconCheck" size="xs" :class="{'invisible': filtroStatoScheda !== SCHEDA_STATI.INVIATA}" />
              <span class=''>
                In attesa
                <span class="ml-md text-bold">{{schedeInAttesa.length}}</span>
              </span>
            </div>

            <div class='btn mx-md' @click="filtraPerStatoScheda(SCHEDA_STATI.ACCETTATA)">
              <font-awesome-icon :icon="iconCheck" size="xs" :class="{'invisible': filtroStatoScheda !== SCHEDA_STATI.ACCETTATA}" />
              <span class=''>
                In carico
                <span class="ml-md text-bold">{{schedeInCarico.length}}</span>
              </span>
            </div>

            <div class='btn mx-md' @click="filtraPerStatoScheda(SCHEDA_STATI.EVASA)">
              <font-awesome-icon :icon="iconCheck" size="xs" :class="{'invisible': filtroStatoScheda !== SCHEDA_STATI.EVASA}" />
              <span class=''>
                Evase
                <span class="ml-md text-bold">{{schedeEvase.length}}</span>
              </span>
            </div>

            <div class='btn mx-md' @click="filtraPerStatoScheda(SCHEDA_STATI.RIFIUTATA)">
              <font-awesome-icon :icon="iconCheck" size="xs" :class="{'invisible': filtroStatoScheda !== SCHEDA_STATI.RIFIUTATA}" />
              <span class=''>
                Rifiutate
                <span class="ml-md text-bold">{{schedeRifiutate.length}}</span>
              </span>
            </div>
          </div>
        </div>

        <div class="col-mobile-12 py-md mx-md">
          <font-awesome-icon :icon="faSearch" class="mx-md" />
          <input v-model="searchValue" type="search" placeholder="Cerca pazienti">
        </div>
      </div>

      <!-- LOADING -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
        <div v-if="loading" >
            <TheLoading />
        </div>

      <!-- TABELLA -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
        <div v-else>
          <table class="table table-pazienti">
            <thead class="table-header">
              <tr class='first-header'>
                <th>&nbsp;</th>
                <th colspan="6">Paziente</th>
                <th colspan="4">Evento</th>
                <th>Tampone</th>
                <th colspan="2">Interventi USCA</th>
                <th colspan="2">&nbsp;</th>
              </tr>
              <tr class="second-header">
                <th class='section-cell'>&nbsp;</th>
                <th @click="sort('asr')" ><p>Asr in carico</p></th>
                <th @click="sort('cognome')" ><p>Nome</p></th>
                <th @click="sort('comuneDomicilioIstat')" ><p>Domicilio</p></th>
                <th @click="sort('comuneResidenzaIstat')"><p>Residenza</p></th>
                <th @click="sort('telefonoRecapito')"><p>Telefono</p></th>
                <th  class='section-cell'><p>Tipo paziente</p></th>
                <th @click="sort('tipoEvento')"><p>Tipo</p></th>
                <th><p>Data evento</p></th>
                <th><p>Data Fine Quarantena</p></th>
                <th title='Dato fornito da SISP' class='section-cell nowrap'><p>Presso <span class='mute'><font-awesome-icon :icon="iconQuestion" /></span></p></th>
                <th class='section-cell'><p>Esito tampone</p></th>
                <th><p>Ultimo</p></th>
                <th class='section-cell'><p>&nbsp;</p></th>
                <th><p>SISP</p></th>
                <th><p>Dettagli</p></th>
              </tr>
            </thead>
            <tbody >
              <!--<PazienteRow :paziente="p" />-->
              <template  v-for="paziente in filteredPazientiAndPaginated">
                <tr class='table-row'  :key="paziente.idSoggetto">
                  <td class='section-cell'>
                    <div class='mobile-row-cell mobile-row'>
                      <PazienteMobileMainRow v-bind:paziente="paziente" v-bind:statoUltimaScheda="getStatoUltimaScheda(paziente)" v-if='schedeReady' />
                      <div class='mobile-expand-icon' @click="toggleMobileDetail(paziente.idSoggetto)">
                          <font-awesome-icon :icon="iconEllipsisV" />
                      </div>
                    </div>
                    <div class='no-mobile'>
                      <div class='icon icon-isolamento-domiciliare' v-if='isolamentoDomiciliare(paziente)'>
                        <font-awesome-icon :icon="iconHome" />
                      </div>
                      <div class='icon icon-isolamento-domiciliare' v-if='assegnazioneDomicilio(paziente)'>
                        <font-awesome-icon :icon="iconBuilding" />
                      </div>
                      <div class='icon icon-segnalazione' v-if='pazienteSegnalato(paziente)'>
                        <font-awesome-icon :icon="iconSegnalazione" />
                      </div>
                      <div class='icon icon-tampone-positivo' v-if='pazienteTamponePositivo(paziente)'>
                        <font-awesome-icon :icon="iconTampone" />
                      </div>
                      <div class='icon icon-da-integrare' v-if='decorsoDaIntegrare(paziente)'>
                        <font-awesome-icon :icon="iconDaIntegrare" />
                      </div>
                    </div>
                  </td>
                  <td class='mobile-detail-row'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <label class='no-desktop'>Asr in carico</label><p>{{(paziente.asr||{}).descrizione }} </p>
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
                  <td class='mobile-detail-row section-cell'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <label class='no-desktop'>Tipo paziente</label><p>{{(paziente.tipoSoggetto||{}).descTipoSoggetto|decodeTipoSoggetto}}</p>
                  </td>
                  <td class='mobile-detail-row '
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <label class='no-desktop'>Evento</label><p>{{(paziente.tipoEvento||{}).descTipoEvento}}</p></td>
                  <td class='mobile-detail-row no-mobile'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <label class='no-desktop'>Data Evento</label><p>{{(paziente.decorso||{}).dataDimissioni | formatMillis}}</p></td>
                  <td class='mobile-detail-row'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <label class='no-desktop'>Fine Quarant.</label><p>{{(paziente.decorso||{}).dataPrevFineEvento | formatMillis}}</p></td>
                  <td class='mobile-detail-row section-cell'
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
                  <td class='mobile-detail-row no-mobile  section-cell'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <p v-bind:class="{ 'tampone-positivo': esitoTampone(paziente)==='Positivo'}">{{esitoTampone(paziente)}}</p>
                  </td>
                  <td class='mobile-detail-row no-desktop'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <label class='no-desktop'>Elenco Decorsi</label>
                      <table class='table-small' v-if="p = pazienteDettaglio['s_'+paziente.idSoggetto]">
                        <tbody>
                          <tr v-for="d in pazienteDettaglio['s_'+p.idSoggetto].elencoDecorso" :key="d.idDecorso" class='table-small-row'>
                            <td>{{d.dataDimissioni| formatMillis}}</td>
                            <td class='table-column-grow-max'>{{(d.decodeTipoEvento||{}).descTipoEvento}}</td>
                            <td  @click="showDecorsoModal(d)"><font-awesome-icon :icon="iconEllipsisV" /></td>
                          </tr>
                          <tr v-if="pazienteDettaglio['s_'+p.idSoggetto].elencoDecorso.length==0"><i>Non sono presenti decorsi</i></tr>
                        </tbody>
                      </table>
                  </td>
                  <td class='mobile-detail-row no-desktop'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <div class='dettagli-button' @click="showAggiungiSegnalazione(paziente, true)" v-if='decorsoDaIntegrare(paziente, )'>
                        Integra
                    </div>
                    <div class='dettagli-button' @click="showAggiungiSegnalazione(paziente, true)" v-if='pazienteDaSegnalare(paziente)'>
                      Segnala
                    </div>
                  </td>
                  <td class='mobile-detail-row no-desktop'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <label class='no-desktop'>Elenco Tamponi</label>
                      <table class='table-small' v-if="p = pazienteDettaglio['s_'+paziente.idSoggetto]">
                            <tbody>
                              <tr v-for="t in pazienteDettaglio['s_'+p.idSoggetto].elencoTampone" :key="t.idTampone" class='table-small-row'>
                                <td>{{t.dataTest|formatMillis}}</td>
                                <td class='table-column-grow-max'>{{(t.risTampone||{}).risultatoTampone}}</td>
                                <td @click="showTamponeModal(t)"><font-awesome-icon :icon="iconEllipsisV" /></td>
                              </tr>
                              <tr v-if="pazienteDettaglio['s_'+p.idSoggetto].elencoTampone.length==0"><i>Non sono presenti tamponi</i></tr>
                            </tbody>
                      </table>
                  </td>
                  <td class='mobile-detail-row no-desktop'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <label class='no-desktop'>Elenco Interventi</label>
                      <table class='table-small' v-if="pazienteSchede['s_'+paziente.idSoggetto]">
                            <tbody>
                              <tr v-for="s in pazienteSchede['s_'+paziente.idSoggetto]" :key="s.id" class='table-small-row'>
                                <td>
                                  {{s.data|formatMillis}}
                                  <template v-if="s.critica">
                                    <span class='cell-row text-danger'>
                                      <strong>Urgente</strong>
                                    </span>
                                  </template>
                                </td>
                                <td> {{(s.stato||{}).nome|empty}}</td>
                                <td class='table-column-grow-max'>{{(s.usca||{}).nome|empty}}</td>
                                <td @click="showDettaglioScheda(s)"><font-awesome-icon :icon="iconEllipsisV" /></td>
                              </tr>
                              <tr v-if="pazienteSchede['s_'+paziente.idSoggetto].length==0"><i>Non sono presenti interventi</i></tr>
                            </tbody>
                      </table>
                  </td>
                  <td class='mobile-detail-row no-desktop'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <div class='dettagli-button' @click="showAggiungiScheda(paziente)" v-if='abilitaCreazioneNuovaScheda(paziente)'>
                        Richiedi intervento
                    </div>
                    <div class='dettagli-button' @click="showAggiungiScheda(paziente)" v-if='getSchedaInBozza(paziente)'>
                          Invia
                      </div>
                  </td>
                  <td class='mobile-detail-row no-desktop'
                    v-bind:class="{ 'mobile-detail-show': showMobileDetail(paziente.idSoggetto),'mobile-detail-hide': !showMobileDetail(paziente.idSoggetto)}">
                    <label class='no-desktop'>Elenco Diari</label>
                      <table class='table-small table-diari-mobile' v-if="pazienteDiari['s_'+paziente.idSoggetto] && pazienteDiari['s_'+paziente.idSoggetto][0]">
                            <tbody>
                              <tr v-for="d in pazienteDiari['s_'+paziente.idSoggetto][0].dettagli" :key="d.id" class='table-small-row'>
                                <td>
                                  {{d.data|formatMillis}}
                                </td>
                                <td>
                                  {{d.note}}<br>
                                  <strong class='mobile-diario-sintomi-title'>Sintomi</strong>
                                  <div class='mobile-diario-sintomi'>{{d.sintomi | listValue('nome') | empty}}</div>
                                </td>
                                <!--<td @click="showDettaglioScheda(s)"><font-awesome-icon :icon="iconEllipsisV" /></td>-->
                              </tr>
                              <!-- <tr v-if="pazienteDiari['s_'+paziente.idSoggetto].length==0"><i>Non sono presenti diari</i></tr> -->
                            </tbody>
                      </table>
                      <div v-else><i>Non sono presenti diari</i></div>
                  </td>
                  <td class='no-mobile'>
                    <div v-if='schedeReady'>
                      {{getStatoUltimaScheda(paziente)}}
                    </div>
                  </td>
                  <td class='no-mobile  section-cell'>
                    <div v-if='schedeReady'>
                      <!-- <div v-if='paziente.schede && paziente.schede.length>0 && paziente.schede[0].stato.codice!="B"' class='text-center'>
                          {{paziente.schede[0].stato.nome}}
                      </div> -->
                      <div class='dettagli-button' @click="showAggiungiScheda(paziente)" title='Nuova richiesta di intervento'
                       v-if='abilitaCreazioneNuovaScheda(paziente)'>
                          Richiedi
                      </div>
                      <div class='dettagli-button' @click="showAggiungiScheda(paziente)" title='Invia richiesta di intervento'
                        v-if='getSchedaInBozza(paziente)'>
                          Invia
                      </div>
                    </div>
                  </td>
                  <td class='no-mobile'>
                    <div class='dettagli-button' @click="showAggiungiSegnalazione(paziente, true)" v-if='decorsoDaIntegrare(paziente)'>
                        Integra
                    </div>
                    <div class='dettagli-button' @click="showAggiungiSegnalazione(paziente, true)" v-if='pazienteDaSegnalare(paziente)'>
                      Segnala
                    </div>
                  </td>
                  <td class='no-mobile'>
                    <div class='dettagli-button' @click="toggleTamponeDecorsoDetail(paziente.idSoggetto)">
                         <span v-bind:class="{ 'show': showTamponeDecorsoDetail(paziente.idSoggetto),'hide': !showTamponeDecorsoDetail(paziente.idSoggetto)}">Dettagli <font-awesome-icon :icon="iconCollapse" /></span>
                         <span v-bind:class="{ 'hide': showTamponeDecorsoDetail(paziente.idSoggetto),'show': !showTamponeDecorsoDetail(paziente.idSoggetto)}">Dettagli <font-awesome-icon :icon="iconExpand" /></span>
                    </div>
                  </td>
                </tr>
                <tr  :key="'2' + paziente.idSoggetto " class='lista-detail-row'
                    v-bind:class="{ 'tampone-detail-show': showTamponeDecorsoDetail(paziente.idSoggetto),'tampone-detail-hide': !showTamponeDecorsoDetail(paziente.idSoggetto)}">
                  <td colspan='16'>
                    <PazienteDetailRow v-bind:paziente="pazienteDettaglio['s_'+paziente.idSoggetto]" v-if=" pazienteDettaglio['s_'+paziente.idSoggetto]" />
                  </td>
                      <!-- {{pazienteDettaglio['s_'+paziente.idSoggetto].elencoTamponi}</div> -->
                </tr>
              </template>
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
import PazienteDetailRow from '@/components/paziente/PazienteDetailRow'
import PazienteMobileMainRow from '@/components/paziente/mobile/PazienteMobileMainRow'

// import PazienteRow from '@/components/paziente/PazienteRow'
import Constants from '@/util/constants'
import { faSearch, faCheck, faFileExcel, faFilter, faHome, faBuilding, faAngleRight, faArrowCircleLeft, faArrowCircleRight, faExclamationTriangle, faPlus, faVial, faEllipsisV, faQuestionCircle, faCaretDown, faCaretUp, faExclamationCircle } from '@fortawesome/free-solid-svg-icons'
import { groupBy } from '../../util/utils'

export default {
  name: 'Pazienti',
  components: {
    TheLoading,
    PazienteDetailRow,
    PazienteMobileMainRow
  },
  data () {
    return {
      SCHEDA_STATI: Constants.SCHEDA_STATI,
      faSearch,
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
      iconSegnalazione: faExclamationCircle,
      iconDaIntegrare: faExclamationTriangle,
      iconAdd: faPlus,
      iconCollapse: faCaretUp,
      iconExpand: faCaretDown,
      iconCheck: faCheck,
      currentUser: null,
      urlExcel: function () { return Constants.VISURAMMG_BASE_URL + '/mmgvisura/soggetti/report/xlsx' },
      filtroStatoScheda: null,
      searchValue: '',
      pazienti: null,
      schedeReady: false,
      schede: [],
      pazienteDettaglio: {},
      pazienteSchede: {},
      pazienteDiari: {},
      loading: true,
      errored: false,
      filterOnlyTamponePositivo: false,
      filterOnlyQuarantena: false,
      currentSort: 'cognome',
      currentSortDir: 'asc',
      pageSize: 50,
      currentPage: 1,
      mobileDetailOpen: {},
      tamponeDecorsoOpen: {},
      comuneDomicilio: function (paziente) {
        return paziente.comuneDomicilio
      },
      comuneResidenza: function (paziente) {
        return paziente.comuneResidenza
      },
      isolamentoDomiciliare: function (paziente) {
        return paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_ISOLAMENTO_DOMICILIARE
      },
      assegnazioneDomicilio: function (paziente) {
        return paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_ASSEGNAZIONE_DOMICILIO
      },
      assegnatoArea: function (paziente) {
        return paziente.area && paziente.area.nome
      },
      esitoTampone: function (paziente) {
        return paziente.tampone && paziente.tampone.idRisTamp ? Constants.ESITO_TAMPONE[paziente.tampone.idRisTamp] : ''
      },
      pazienteSegnalato: function (paziente) {
        return paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_SEGNALAZIONE
      },
      pazienteTamponePositivo: function (paziente) {
        return paziente.tampone && paziente.tampone.idRisTamp && Constants.ESITO_TAMPONE[paziente.tampone.idRisTamp] === 'Positivo'
      },
      decorsoDaIntegrare: function (paziente) {
        return paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_INFO_DA_INTEGRARE
      },
      pazienteDaSegnalare (paziente) {
        return !paziente.decorso
      },
      abilitaCreazioneNuovaScheda: function (paziente) {
        let result = true
        if (paziente.schede && paziente.schede.length > 0) {
          for (let i = 0; i < paziente.schede.length; i++) {
            if (paziente.schede[i].stato.codice === 'B' ||
                paziente.schede[i].stato.codice === 'I' ||
                paziente.schede[i].stato.codice === 'P') {
              result = false
              break
            }
          }
        }
        return result
      },
      getSchedaInBozza: function (paziente) {
        let result = null
        if (paziente.schede && paziente.schede.length > 0) {
          for (let i = 0; i < paziente.schede.length; i++) {
            if (paziente.schede[i].stato.codice === 'B') {
              result = paziente.schede[i]
              break
            }
          }
          return result
        }
      },
      getStatoUltimaScheda: function (paziente) {
        let result = '-'
        if (paziente.schede && paziente.schede.length > 0) {
          result = paziente.schede[paziente.schede.length - 1].stato.codice
          result = Constants.SCHEDA_STATI_LABEL[result]
          if (result) result = result.toUpperCase()
        }
        return result
      }
    }
  },
  url: {
    currentPage: 'pagina',
    pageSize: 'paginaDim',
    filterOnlyTamponePositivo: 'positivi',
    filterOnlyQuarantena: 'quarantena',
    searchValue: 's',
    filtroStatoScheda: 'stato'
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
    filtriCheck: function (paziente) {
      const tamponePositivo = !this.filterOnlyTamponePositivo || (paziente.tampone && paziente.tampone.idRisTamp === 2)
      const quarantena = !this.filterOnlyQuarantena ||
        (paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_ISOLAMENTO_DOMICILIARE) ||
        (paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_ASSEGNAZIONE_DOMICILIO)
      return tamponePositivo && quarantena

      // // Le schede sono già ordinate
      // const schede = this.schedePerPaziente[paziente.id] || []
      // const ultimaScheda = schede[0]
      // const statoScheda = !this.filtroStatoScheda || (ultimaScheda && ultimaScheda.stato && ultimaScheda.stato.codice === this.filtroStatoScheda)
      //
      // return tamponePositivo && quarantena && statoScheda
    },
    numPazientiEsitoPositivo: function () {
      return this.pazienti.filter((obj) => (obj.tampone && obj.tampone.idRisTamp === 2)).length
    },
    numPazientiQuarantena: function () {
      return this.pazienti.filter((obj) => ((obj.decorso && obj.decorso.idTipoEvento === Constants.ID_ISOLAMENTO_DOMICILIARE) ||
        (obj.decorso && obj.decorso.idTipoEvento === Constants.ID_ASSEGNAZIONE_DOMICILIO))).length
    },
    toggleMobileDetail: function (idSoggetto) {
      console.log('tt', this.mobileDetailOpen)
      if (!this.mobileDetailOpen['s_' + idSoggetto]) {
        this.$set(this.mobileDetailOpen, 's_' + idSoggetto, true)
      } else {
        this.$set(this.mobileDetailOpen, 's_' + idSoggetto, !this.mobileDetailOpen['s_' + idSoggetto])
      }
      this.$http.get(Constants.VISURAMMG_BASE_URL + '/mmgvisura/soggetti/' + idSoggetto).then(response => {
        this.$set(this.pazienteDettaglio, 's_' + idSoggetto, response.data)
        console.log('getTamponeDetail', response.data)
      }).catch(error => {
        console.log(error)
        this.errored = true
      })
      console.log('getSchede')
      this.$http.get(Constants.VISURAMMG_EXTERNAL_URL + 'schede?soggetto=' + idSoggetto).then(response => {
        this.$set(this.pazienteSchede, 's_' + idSoggetto, response.data)
        console.log('getSchede', response.data)
      }).catch(error => {
        console.log(error)
      }).then(this.loading = false)
      console.log('getDiari')
      this.$http.get(Constants.VISURAMMG_EXTERNAL_URL + 'diari?soggetto=' + idSoggetto).then(response => {
        this.$set(this.pazienteDiari, 's_' + idSoggetto, response.data)
        console.log('getDiari', response.data)
      }).catch(error => {
        console.log(error)
      }).then(this.loading = false)
    },
    showMobileDetail: function (idSoggetto) {
      return this.mobileDetailOpen['s_' + idSoggetto] === true
    },
    showTamponeDecorsoDetail: function (idSoggetto) {
      return this.tamponeDecorsoOpen['s_' + idSoggetto] === true
    },
    toggleTamponeDecorsoDetail: function (idSoggetto) {
      if (!this.tamponeDecorsoOpen['s_' + idSoggetto]) {
        this.$set(this.tamponeDecorsoOpen, 's_' + idSoggetto, true)
      } else {
        this.$set(this.tamponeDecorsoOpen, 's_' + idSoggetto, !this.tamponeDecorsoOpen['s_' + idSoggetto])
      }
      this.$http.get(Constants.VISURAMMG_BASE_URL + '/mmgvisura/soggetti/' + idSoggetto).then(response => {
        this.$set(this.pazienteDettaglio, 's_' + idSoggetto, response.data)
        console.log('getTamponeDetail', response.data)
      }).catch(error => {
        console.log(error)
        this.errored = true
      })
    },
    showDecorsoModal: function (decorso) {
      console.log('decorso modal', decorso)
      this.$modal.show('decorso-detail', { decorso: decorso })
    },
    showTamponeModal: function (tampone) {
      console.log('tampone  modal', tampone)
      this.$modal.show('tampone-detail', { tampone: tampone })
    },
    showDettaglioScheda (scheda) {
      this.$modal.show('scheda-detail', { scheda })
    },

    showAggiungiScheda: function (paziente) {
      this.$http
        .get(Constants.VISURAMMG_BASE_URL + '/currentUser')
        .then(response => {
          console.log('getCurrentUser', Constants.VISURAMMG_BASE_URL + '/currentUser')
          if (!response.data || !response.data.cfUtente) {
            window.location.href = '/visurammgweb/relogin.html?c=' + new Date().getTime() + '0'
          } else {
            this.$modal.show('modifica-scheda-paziente', { paziente: paziente, scheda: this.getSchedaInBozza(paziente) })
          }
        })
        .catch(error => {
          console.log('getCurrentUserError:' + error)
          window.location.href = '/visurammgweb/relogin.html?c=' + new Date().getTime() + '0'
        })
        .then(a => {
          console.log('getCurrentUser FINAL:')
        })
    },
    showAggiungiSegnalazione: function (paziente, segnalazione = false) {
      this.$http
        .get(Constants.VISURAMMG_BASE_URL + '/currentUser')
        .then(response => {
          console.log('getCurrentUser', Constants.VISURAMMG_BASE_URL + '/currentUser')
          if (!response.data || !response.data.cfUtente) {
            window.location.href = '/visurammgweb/relogin.html?c=' + new Date().getTime() + '0'
          } else {
            this.$modal.show('segnalazione-paziente', { paziente, segnalazione })
          }
        })
        .catch(error => {
          console.log('getCurrentUserError:' + error)
          window.location.href = '/visurammgweb/relogin.html?c=' + new Date().getTime() + '0'
        })
        .then(a => {
          console.log('getCurrentUser FINAL:')
        })
    },
    showDettaglioSintomo: function (d) {
      this.$modal.show('sintomo-detail', { decorso: d })
    },
    addSchedaToPaziente: function (s) {
      for (let i = 0; i < this.pazienti.length; i++) {
        if (this.pazienti[i].idSoggetto === s.soggetto.id) {
          if (!this.pazienti[i].schede) {
            this.pazienti[i].schede = []
          }
          this.pazienti[i].schede.push(s)
          break
        }
      }
    },
    loadSchedeMedico: function () {
      console.log('loadSchedeMedico', this.currentUser)
      this.$http
        .get(Constants.VISURAMMG_EXTERNAL_URL + 'schede?medico=' + this.currentUser.medico.cfMedico)
        .then(response => {
          if (response && response.data) {
            console.log('loadSchedeMedico', response.data)
            this.schede = response.data
            // this.schede = response.data
            const schedeOrdinate = response.data.sort((a, b) => (a.data > b.data) ? 1 : -1)
            for (let i = 0; i < schedeOrdinate.length; i++) {
              this.addSchedaToPaziente(schedeOrdinate[i])
            }
          }
          console.log('pazienti con schede', this.pazienti)
          this.schedeReady = true
          this.$forceUpdate()
        })
        .catch(error => {
          console.log(error)
          this.errored = true
        })
        .finally(this.loading = false)
    },
    filtraPerStatoScheda (codice) {
      this.filtroStatoScheda = this.filtroStatoScheda === codice ? null : codice
    }
  },
  computed: {
    schedeOrdinate () {
      return [...this.schede].sort((a, b) => new Date(a.data) - new Date(b.data) > 0 ? -1 : 1)
    },
    schedePerPaziente () {
      return groupBy(this.schedeOrdinate, s => s.soggetto.id)
    },
    ultimeSchede () {
      return Object.values(this.schedePerPaziente).map((group) => group[0])
    },
    ultimeSchedeFiltratePerPazientiVisibili () {
      const ids = this.filteredPazienti.map(p => p.idSoggetto)
      return this.ultimeSchede.filter(s => ids.includes(s.soggetto.id))
    },
    schedeInAttesa () {
      return this.ultimeSchedeFiltratePerPazientiVisibili.filter(s => s.stato && s.stato.codice === Constants.SCHEDA_STATI.INVIATA)
    },
    schedeInCarico () {
      return this.ultimeSchedeFiltratePerPazientiVisibili.filter(s => s.stato && s.stato.codice === Constants.SCHEDA_STATI.ACCETTATA)
    },
    schedeEvase () {
      return this.ultimeSchedeFiltratePerPazientiVisibili.filter(s => s.stato && s.stato.codice === Constants.SCHEDA_STATI.EVASA)
    },
    schedeRifiutate () {
      return this.ultimeSchedeFiltratePerPazientiVisibili.filter(s => s.stato && s.stato.codice === Constants.SCHEDA_STATI.RIFIUTATA)
    },
    filteredPazienti: function () {
      if (!this.pazienti) {
        console.log('this.pazienti null')
        return this.pazienti
      }

      let result = this.pazienti
      let toSearch = this.searchValue || ''
      toSearch = toSearch.toUpperCase()

      // FILTRA PER QUARANTENA E TAMPONE
      // -------------------------------
      result = result.filter(obj => this.filtriCheck(obj))

      // FILTRA PER STATI
      // ----------------
      if (this.filtroStatoScheda) {
        result = result.filter(p => {
          const schede = this.schedePerPaziente[p.idSoggetto] || []
          const ultimaScheda = schede[0]
          const statoScheda = ultimaScheda && ultimaScheda.stato && ultimaScheda.stato.codice
          return statoScheda === this.filtroStatoScheda
        })
      }

      // FILTRO PER RICERCA
      // ------------------
      result = result.filter(p => {
        const searchable = [p.cognome, p.nome].filter(v => !!v).join(' ').toUpperCase()
        return searchable.includes(toSearch)
      })

      // ORDINIAMO I RISULTATI
      // ---------------------
      result.sort((a, b) => {
        let modifier = 1
        if (this.currentSortDir === 'desc') modifier = -1
        if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier
        if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier
        return 0
      })

      return result
    },
    filteredPazientiAndPaginated: function () {
      if (!this.filteredPazienti) {
        console.log('this.pazienti null')
        return this.filteredPazienti
      }

      return this.filteredPazienti.filter((row, index) => {
        var start = (this.currentPage - 1) * this.pageSize
        var end = this.currentPage * this.pageSize
        if (index >= start && index < end) return true
      })
    }
  },
  mounted () {
    // load pazienti
    this.$http
      .get(Constants.VISURAMMG_BASE_URL + '/mmgvisura/soggetti')
      .then(response => {
        this.pazienti = response.data
        this.currentUser = this.$store.getters.currentUser
        console.log('getPazienti', response.data)
        this.loadSchedeMedico()
      })
      .catch(error => {
        console.log(error)
        this.errored = true
      })
      .finally(this.loading = false)
  },
  watch: {
    filteredPazienti: {
      handler (newVal, oldVal) {
        if (newVal === oldVal) return

        // Ogni volta che si effettua una ricerca la tabella deve tornare alla prima pagina
        this.currentPage = 1
      }
    }
  }
}
</script>

<style scoped>
  .table-pazienti{font-size: 13px;}
  .title-with-options{display: flex;padding: 4px 8px;align-items: center;margin-bottom: 2rem}
  .title-with-options h2{flex-grow: 1;margin: 0; text-align: left;}
  .toolbar-icon{margin: 0 12px;}
  .pagination{justify-content: center;}
  .table-info{flex-grow: 1; display: flex; }
  .table-info-separator{border-left: solid 1px #ccc; margin-left:.5em; padding-left: .5em;}
  .table-info-intro{margin-right: 1em;}
  .table .table-header {text-align: left;}
  .table-info-items{display: flex;}
  .pagination-top{display: none;}
  a{text-decoration: none;}

  .mobile-row-cell{display: none;}
  .no-desktop{display: none;}
  .mute{color: #bbb;}

  .tampone-detail-show{display: table-row;}
  .tampone-detail-hide{display: none;}
  .show{display: block;}
  .hide{display: none;}
  .lista-detail-row{background-color: #E6E9EE;}
  .detail-title{margin: 15px 15px 0 15px; font-size: 1em; font-weight: bold;}
  .lista-detail-panel{padding: 15px; margin:5px 15px 15px 15px;box-shadow:   0 8px 6px -6px rgba(0,0,0,.3); background-color: #fff;}
  .table-small{border-collapse: collapse;border-spacing: 0;}
  .table-small th{padding: 0px 12px;}
  .table-small td{ padding: 6px 12px; border-bottom: solid 1px #ddd; }

  .dettagli-button{border: solid 1px #ccc; padding: .5em 1em; border-radius: 2em;background-color: #eee; font-size: 12px;white-space: nowrap;}
  .dettagli-button:hover{background: #ddd;cursor: pointer;}

  .table-column-grow-max{width: 100%;}

  .sintomo-button{padding: .1em 1em;}

  .first-header th{border-bottom: none; border-right: 1px solid #ccc;padding-top: .5em;}
  .second-header th{font-size:.9em; font-weight: normal;padding-top: .5em;}
  .section-cell{border-right: 1px solid #ccc}
  thead th{background-color: #eee;}
  tbody{font-size:.9em;}
  .dettagli-button{text-align: center}
  @media only screen and (max-width: 768px) {
     .title-with-options{margin-bottom:0;padding-bottom: 1rem;text-align: left;align-items: flex-start;background: #fff;}
     .table-info{ width: 100%;margin-bottom: .5rem; flex-direction: column;}
     .table-info-items{flex-direction: column;font-size: .8em; text-align: left;}
     .toolbar{flex-direction: column; background: #fff;}
     .table-info-separator{display: none;}
     .pagination-top{display: flex; padding: 0 .5rem 1rem .5rem;box-shadow:   0 8px 6px -6px rgba(0,0,0,.3);margin-bottom: .5rem;}
     .pagination-bottom.toolbar{flex-direction: row;}
     .pagination-top.toolbar{flex-direction: row;}
     .toolbar-separator-grow{flex-grow: 1;}
     .btn-filter-desktop{display: none;}
     .mobile-white{background: #fff;}
     .toolbar-separator-vertical-mobile{height: 6px;}

    .table .table-header {display: none;}
    .mobile-grey{background: #f0f3f8;}
    .table{background: #f0f3f8;}
    .table .table-row {box-shadow: 0 1px 8px rgba(0,0,0,.2), 0 3px 4px rgba(0,0,0,.14), 0 3px 3px -2px rgba(0,0,0,.12);
      margin: 1rem; display: block;}
    .table .table-row p { border: 0; display: inline; padding: 0;}
    .table .table-row td { display: block; position: relative;padding: 4px 8px;background: #fff; }

    .no-desktop{display: block;}
    .table td{border: none}
    .table .table-row td.no-mobile{display: none;}
    .table .table-row td .no-mobile{display: none;}
    .mobile-row-cell{display: block; height: auto;}
    .mobile-row{display: flex; align-content: center;}
    .mobile-row-info{flex-grow: 1; padding: 8px 0;}
    .mobile-row-main{display: flex; align-content: top;margin-bottom: 8px;}
    .mobile-row-nome{font-weight: bold; font-size: 1.2em;flex-grow: 1;}
    .mobile-row-dimissioni{font-size: .8em; margin-bottom: 2px;}
    .mobile-row-top{font-size: .7em; margin-bottom: 4px;}
    .mobile-row-fine-quarantena{font-size: .8em}
    .mobile-quarantena-icon{font-size:1rem;text-align: center; display: flex; }
    .mobile-quarantena-icon .icon{margin-right:.6rem;}
    .mobile-expand-icon{font-size: 1rem;text-align: center;padding: 8px 1rem;}

    .mobile-detail-row{font-size: .8em;}
    label.no-desktop{font-weight: bold; display: inline-block;padding-right: .5em;}
    label.no-desktop i{font-weight: normal;padding-left: 1em;font-size: smaller;}
    .table .table-row td.mobile-detail-hide{display: none;}
    .table .table-row td.mobile-detail-show{display: block;}

    .lista-detail-container{flex-direction: column;}
    .table-small{width: 100%; font-size: 1em;}
    .table-small .table-small-row td{ display: table-cell; padding: 8px 8px; border-bottom: solid 1px #ddd;}

    .table .table-row td.no-mobile.mobile-detail-show{display: none;}
    .dettagli-button{text-align: center; padding:12px 0; margin: 12px 0;}
    .icons-mobile{display: flex;}
    .table-small.table-diari-mobile .table-small-row td{vertical-align: top;}
    .mobile-diario-sintomi-title{margin-top: 8px; display: block;}
   }
</style>
