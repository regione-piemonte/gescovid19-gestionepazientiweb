<template>
    <div class='user-row'>
        <div class='info'>
            <div class='main-row'>
                <strong>{{paziente.cognome + ' ' + paziente.nome |string_ellipse(16)}}</strong>
            </div>
            <div>
                <div class='info-row'>
                    <div class='' > </div>
                    <small>Domicilio: {{(paziente.comuneDomicilio||{}).nomeComune}}</small>
                </div>
                <div v-if='comuneResidenza()' class='info-row'>
                    <div class='' > </div>
                    <small>Residenza: {{(paziente.comuneResidenza||{}).nomeComune}}</small>
                </div>
                <div class='info-row' >
                    <div class='' > </div>
                    <small>Telefono: {{paziente.telefonoRecapito}}</small>
                </div>
                <div class='info-row' >
                    <div class='' > </div>
                    <small>Dimissioni: {{(paziente.tipoEvento||{}).descTipoEvento}}</small>
                </div>
                <div class='info-row' >
                    <div class='' > </div>
                    <small>Data dimissioni: {{(paziente.decorso||{}).dataDimissioni | formatMillis}}</small>
                </div>
                <div class='info-row' >
                    <div class='' > </div>
                    <small>Data fine quarantena: {{(paziente.decorso||{}).dataPrevFineEvento | formatMillis}}</small>
                </div>
                <div v-if='isolamentoDomiciliare()' class='info-row'>
                    <div class='icon icon-isolamento-domiciliare'> <font-awesome-icon :icon="iconHome" /> </div>
                    <small>Decorso Presso: {{(paziente.comuneRicovero||{}).nomeComune}} {{paziente.decorso.indirizzoDecorso}} ({{paziente.decorso.decorsoPresso}})</small>
                </div>
                <div v-if='assegnazioneDomicilio()' class='info-row'>
                    <div class='icon icon-isolamento-domiciliare'> <font-awesome-icon :icon="iconBuilding" /> </div>
                    <small>Decorso Presso: {{paziente.decorso.struttura}} - {{paziente.decorso.area}}</small>
                </div>
                <div v-else-if='assegnatoArea()' class='info-row'>
                    <div class='icon'> <font-awesome-icon :icon="icon" /> </div>
                    <small>Presso: {{paziente.area.nome}}</small>
                </div>
            </div>
        </div>
        <div class='tampone' v-bind:class="{ 'tampone-positivo': esitoTampone()==='Positivo'}" >
            <span class='label'>Esito tampone</span>{{esitoTampone()}}
        </div>
        <div class='arrow'>
            <router-link :to="{ name: 'PazienteDetail', params: { pazienteId: paziente.idSoggetto }, query: {paziente:paziente}}">
                <font-awesome-icon :icon="iconArrowRight" />
            </router-link>
        </div>
    </div>
</template>
<script>
import Constants from '@/util/constants'
import { faHome, faBuilding, faAngleRight } from '@fortawesome/free-solid-svg-icons'

export default {
  name: 'PazienteRow',
  props: ['paziente'],
  data () {
    return {
      iconHome: faHome,
      iconBuilding: faBuilding,
      iconArrowRight: faAngleRight,
      comuneDomicilio: function () {
        return this.paziente.comuneDomicilio
      },
      comuneResidenza: function () {
        return this.paziente.comuneResidenza
      },
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
    console.log('ciao', this.paziente)
  }
}
</script>
<style scoped>
    .user-row{display: flex;padding: 16px 8px; border-bottom: solid 1px #ddd;align-items: center;}
    .user-row .info{flex-grow: 1;}
    .main-row{display: flex;margin-bottom: 8px; white-space: nowrap;}
    .info-row{display: flex; font-size: .8em; align-items: center; float: left; flex-direction: row;}
    .info-row .icon{margin-right: .5em;}
    .info-row small{font-size: .7em;}
    .arrow{font-size: 2em; margin-left: .3em;}
    .arrow a{color: #ccc;}
    .tampone{padding: 4px; background-color:#ccc; border-radius: 4px;font-size: .7em; color: white;}
    .tampone-positivo{background-color: #333; letter-spacing: 1px;}
    .tampone .label{font-size: .8em; display: block;margin-bottom: 4px;}
</style>
