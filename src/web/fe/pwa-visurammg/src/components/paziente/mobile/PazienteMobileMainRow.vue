<template>
    <div class='mobile-row-info'>
        <div class='mobile-row-top'>
            Asr in carico <p>{{(paziente.asr||{}).descrizione }}</p>
        </div>
        <div class='mobile-row-main icons-mobile'>
            <div class='mobile-quarantena-icon'>
                <div class='icon icon-isolamento-domiciliare' v-if='isolamentoDomiciliare(paziente)'>
                <font-awesome-icon :icon="iconHome" />
                </div>
                <div class='icon icon-isolamento-domiciliare' v-if='assegnazioneDomicilio(paziente)'>
                <font-awesome-icon :icon="iconBuilding" />
                </div>
                <div   class='icon icon-segnalazione' v-if='pazienteSegnalato(paziente)'>
                <font-awesome-icon :icon="iconSegnalazione" />
                </div>
                <div class='icon icon-tampone-positivo' v-if='pazienteTamponePositivo(paziente)'>
                <font-awesome-icon :icon="iconTampone" />
                </div>
                <div class='icon icon-da-integrare' v-if='decorsoDaIntegrare(paziente)'>
                <font-awesome-icon :icon="iconDaIntegrare" />
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
            <strong>Evento</strong> {{(paziente.tipoEvento||{}).descTipoEvento|tipoEventoShort}}
            <span v-if='paziente.decorso && paziente.decorso.dataDimissioni'> - {{(paziente.decorso||{}).dataDimissioni | formatMillis}}</span>
        </div>
        <div class='mobile-row-fine-quarantena' v-if='paziente.decorso && paziente.decorso.dataPrevFineEvento'>
            <strong>Fine quarantena</strong> {{(paziente.decorso||{}).dataPrevFineEvento | formatMillis}}
        </div>
        <div class='mobile-row-dimissioni'>
            <strong>Ultimo intervento </strong>
            <p >{{statoUltimaScheda}}</p>
        </div>
    </div>
</template>

<script>
import Constants from '@/util/constants'

import { faHome, faBuilding, faExclamationTriangle, faVial, faExclamationCircle } from '@fortawesome/free-solid-svg-icons'

export default {
  name: 'PazienteMobileMainRow',
  props: ['paziente', 'statoUltimaScheda'],
  data () {
    return {
      iconHome: faHome,
      iconTampone: faVial,
      iconBuilding: faBuilding,
      iconSegnalazione: faExclamationCircle,
      iconDaIntegrare: faExclamationTriangle,
      errored: false,
      isolamentoDomiciliare: function (paziente) {
        return paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_ISOLAMENTO_DOMICILIARE
      },
      assegnazioneDomicilio: function (paziente) {
        return paziente.decorso && paziente.decorso.idTipoEvento === Constants.ID_ASSEGNAZIONE_DOMICILIO
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
      }

    }
  }
}
</script>

<style scoped>
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
   }
</style>
