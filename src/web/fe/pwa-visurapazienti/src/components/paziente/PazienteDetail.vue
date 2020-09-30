<template>
<div class='dettaglio-paziente'>
    <div class='toolbar'>
        <div class='toolbar-btn'>
            <router-link :to="{name: 'Home'}">
                <font-awesome-icon :icon="iconArrowLeft" /> <span class='toolbar-btn-label'>Lista pazienti</span>
            </router-link>
        </div>
    </div>
    <div class='header'>
        <div class='nome'>
            <strong>{{paziente.nome}} {{paziente.cognome}}</strong>
        </div>
        <div class='icon icon-isolamento-domiciliare'> <font-awesome-icon :icon="iconHome" /> </div>
    </div>
    <div class='section'>
        <div class='info'>
            <div class=''>
               <div class='info-row'><strong>Data di nascita</strong> {{paziente.dataNascita | formatMillis}}</div>
               <div class='info-row'><strong>Codice Fiscale</strong> {{paziente.codiceFiscale}}</div>
               <div class='info-row'><strong>Telefono</strong> <a :href='"tel:" +paziente.telefonoRecapito'>{{paziente.telefonoRecapito}}</a></div>
               <div class='info-row'><strong>Email</strong> {{paziente.email}}</div>
               <div class='info-row'><strong>Comune di residenza</strong> <span v-if='paziente.comuneResidenza'>{{paziente.comuneResidenza.nomeComune}}</span></div>
               <div class='info-row'><strong>Comune di domicilio</strong><span v-if='paziente.comuneDomicilio'>{{paziente.comuneDomicilio.nomeComune}}</span></div>
            </div>
        </div>
    </div>
    <div class=section>
        <div class='decorso'>
            <strong>Dati ultimo decorso</strong>
            <div class='info-row'><strong>Dimissioni</strong> {{(paziente.tipoEvento||{}).descTipoEvento}}</div>
            <div class='info-row'><strong>Data Inizio Dimissioni</strong> {{(paziente.decorso||{}).dataDimissioni|formatMillis}}</div>
            <div class='info-row'><strong>Data Fine Quarantena</strong> {{(paziente.decorso||{}).dataPrevFineEvento|formatMillis}}</div>
            <div v-if='isolamentoDomiciliare()' class='info-row'>
                <strong>Decorso Presso</strong> {{(paziente.comuneRicovero||{}).nomeComune}} {{paziente.decorso.indirizzoDecorso}} ({{paziente.decorso.decorsoPresso}})
            </div>
            <div v-if='assegnazioneDomicilio()' class='info-row'>
                <strong>Decorso Presso</strong> {{paziente.struttura.nome}} - {{paziente.area.nome}}
            </div>
            <div v-else-if='assegnatoArea()' class='info-row'>
                <strong>Decorso Presso</strong> {{paziente.area.nome}}
            </div>
            <div class='info-row'><strong>Note</strong> {{(paziente.decorso||{}).note}}</div>
        </div>
    </div>
    <div class='section'>
        <div class='tampone'>
            <strong>Dati ultimo tampone</strong>
            <div class='info-row'><strong>Esito ultimo tampone</strong> {{esitoTampone()}}</div>
        </div>
    </div>
</div>
</template>

<script>

import Constants from '@/util/constants'
import { faHome, faBuilding, faArrowLeft } from '@fortawesome/free-solid-svg-icons'
export default {
  name: 'PazienteDetail',
  props: ['idPaziente'],
  data () {
    return {
      iconHome: faHome,
      iconBuilding: faBuilding,
      iconArrowLeft: faArrowLeft,
      paziente: this.$route.query.paziente,
      isolamentoDomiciliare: function () {
        return this.paziente.decorso &&
           (this.paziente.decorso.idTipoEvento === Constants.ID_ISOLAMENTO_DOMICILIARE ||
            this.paziente.decorso.idTipoEvento === Constants.ID_DISPOSTA_QUARANTENA_DOMIC
           )
      },
      assegnazioneDomicilio: function () {
        return this.paziente.decorso &&
           (this.paziente.decorso.idTipoEvento === Constants.ID_ASSEGNAZIONE_DOMICILIO ||
            this.paziente.decorso.idTipoEvento === Constants.ID_DISPOSTA_QUARANTENA_EXTRA_DOMIC
           )
      },
      assegnatoArea: function () {
        return this.paziente.area && this.paziente.area.nome
      },
      esitoTampone: function () {
        return this.paziente.tampone && this.paziente.tampone.idRisTamp ? Constants.ESITO_TAMPONE[this.paziente.tampone.idRisTamp] : ''
      }

    }
  },
  mounted () {
    console.log('paziente', this.$route.query)
  }
}
</script>
<style scoped>
.dettaglio-paziente .toolbar{ padding: 12px}
.dettaglio-paziente{text-align: left;}
.dettaglio-paziente .header{background-color: #eee;padding:12px; display: flex; margin: 1em 0;}
.dettaglio-paziente .nome{ flex-grow: 1;}
.dettaglio-paziente .section{padding:12px; border-bottom: solid 1px #ccc;}
.dettaglio-paziente .info-row{font-size: .8em; padding: .5em;}
a{text-decoration: none;}

</style>
