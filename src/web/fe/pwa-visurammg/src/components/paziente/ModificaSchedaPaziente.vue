<template>
  <modal name="modifica-scheda-paziente" :adaptive='true' height="auto" :scrollable="true"  width='650px'   :styles="'display: flex; height: auto;padding: 12px;background-color: transparent;box-shadow: none' " @before-open="beforeOpen">
    <div class='modal-content' v-if='paziente'>
        <div class='modal-title' @click="debug()">Nuova Richiesta di intervento&nbsp;<small>{{paziente.nome}} {{paziente.cognome}}</small></div>
        <div class='paziente'>
            <form class='form' v-if='!finish'>
              <div class='form-section'>

                <!-- ASL -->
                <!-- ----------------------------------------------------------------------------------------------- -->
                <div class='form-group '>
                  <label class='label-column  control-label'  for='asl'>ASL*</label>
                  <div class='control-column'>
                    <select v-model="asl" class='form-control ' name='asl' :disabled='scheda.id'>
                      <option v-for="s in aslOpzioni" :value="s.id" :key="s.id">{{ s.nome}}</option>
                    </select>
                  </div>
                </div>

                <!-- USCA -->
                <!-- ----------------------------------------------------------------------------------------------- -->
                  <div class='form-group '>
                   <label class='label-column  control-label'  for='struttura'>USCA*</label>
                   <div class='control-column'>
                     <select v-model="scheda.struttura" class='form-control ' name='struttura' :disabled='scheda.id || !asl'>
                         <option v-for="s in struttureOpzioni" :value="s.id" :key="s.id">{{ s.nome}}</option>
                     </select>
                   </div>
                 </div>

                <!-- CRITICA -->
                <!-- ----------------------------------------------------------------------------------------------- -->
<!--                 <div class='form-group'>-->
<!--                   <label class='label-column  control-label'  for='scheda_critica'>Critica</label>-->
<!--                   <div class='control-column-large  inline-controls'>-->
<!--                     <div class='radio inline-radio'>-->
<!--                       <label><input type='radio' v-model='scheda.critica' value='false' name='scheda_critica' :disabled='scheda.id'/>-->
<!--                         <span class='cr'><font-awesome-icon :icon="iconCheck" class='cr-icon'/></span>-->
<!--                         <span>No</span>-->
<!--                       </label>-->
<!--                     </div>-->
<!--                     <div class='radio inline-radio'>-->
<!--                       <label><input type='radio' v-model='scheda.critica'  value='true' name='scheda_critica' :disabled='scheda.id' />-->
<!--                         <span class='cr'><font-awesome-icon :icon="iconCheck"  class='cr-icon'/></span>-->
<!--                         <span>Si</span>-->
<!--                       </label>-->
<!--                     </div>-->
<!--                   </div>-->
<!--                </div>-->

                <div class='form-group form-group-end-section'>
                    <label class='label-column-textarea  control-label'  for='schedaDescrizione'><br>Anamnesi patologica prossima e remota*</label>
                    <div class='control-column-large control-with-text-area'>
                      <div class='form-control'>
                        <textarea rows='3'  id='schedaDescrizione' :disabled='scheda.id'
                          v-model='scheda.descrizione'></textarea>
                      </div>
                    </div>
                </div>
              <!--
                <div class='form-group '>
                   <label class='label-column  control-label'  for='tipiPrescrizione'>Prescrizione</label>
                   <div class='control-column'>
                     <select v-model="prescrizione.tipo" class='form-control ' name='tipiPrescrizione'>
                         <option v-for="s in allTipiPrescrizione" :value="s.id" :key="s.id">{{ s.nome}}</option>
                     </select>
                   </div>
                </div> -->
                <div class='form-group' v-for="s in allTipiPrescrizione" :key="s.id">
                    <label class='label-column-textarea  control-label'  ><br>Prescrizione <span class='lowercase'>{{s.nome}}</span></label>
                    <div class='control-column-large control-with-text-area'>
                      <div class='form-control'>
                        <textarea rows='3' :disabled='scheda.id'
                          v-model='prescrizioni[s.codice].prescrizione' ></textarea>
                      </div>
                    </div>
                </div>
                <!-- <div class='form-group'>
                    <label class='label-column-textarea  control-label'  for='prescrizioneossigenoterapia'><br>Prescrizione ossigenoterapia</label>
                    <div class='control-column-large control-with-text-area'>
                      <div class='form-control'>
                        <textarea rows='3'  id='prescrizioneossigenoterapia'
                          v-model='prescrizione.ossigenoterapia' ></textarea>
                      </div>
                    </div>
                </div> -->
              </div>
            </form>
        </div>
        <div class='main-feedback' v-if='feedback'>
            <div class='alert' v-bind:class='feedback.type'>
                {{feedback.message}}
                <ul v-if="feedback.details">
                  <li v-for="(detail, index) in feedback.details" :key="index">{{detail}}</li>
                </ul>
            </div>
        </div>
        <div class='modal-footer ' >
          <div class='toolbar-left'  v-if='!finish'>
            <a href  @click.prevent="$modal.hide('modifica-scheda-paziente')" class='btn btn-default'>Annulla</a>
          </div>
          <div class='toolbar-right' v-if='!finish' >
<!--            <a href='#' @click.prevent='inviaScheda(true)' class='btn btn-primary' v-if='!loading && scheda.id'>Invia</a>-->
<!--            <a href='#' @click.prevent='saveScheda(true)' class='btn btn-primary' v-if='!loading && !scheda.id'>Salva senza inviare</a>-->
<!--            <span class='toolbar-btn-separator'>&nbsp;</span>-->
            <a href='#' @click.prevent='saveScheda(false)' class='btn btn-primary' v-if='!loading && !scheda.id'>Salva e invia</a>
            <font-awesome-icon :icon="iconSpinner" class='fa-spin' v-if='loading'/>
          </div>
          <div class='toolbar-right' v-if='finish' >
            <a href  @click.prevent="onHide" class='btn btn-default'>Chiudi</a>
          </div>
        </div>
    </div>
  </modal>
</template>

<script>
import Constants from '@/util/constants'
import { faCircleNotch, faCheck, faInfoCircle, faLongArrowAltLeft, faSearch, faExclamationCircle } from '@fortawesome/free-solid-svg-icons'
import { uniqueElementsBy } from '../../util/utils'
// import moment from 'moment'

export default {
  name: 'EditSchedaPaziente',
  components: {
  },
  data: function () {
    return {
      iconSpinner: faCircleNotch,
      iconCheck: faCheck,
      iconInfo: faInfoCircle,
      iconArrowLeft: faLongArrowAltLeft,
      iconSearch: faSearch,
      iconImportant: faExclamationCircle,
      paziente: null,
      cfAuraFeedback: null,
      feedback: null,
      allStrutture: [],
      allTipiPrescrizione: [],
      prescrizioni: {},
      finish: false,
      scheda: {},
      asl: null
    }
  },
  computed: {
    aslOpzioni () {
      let enti = this.allStrutture.map(s => s.ente).filter(e => !!e)
      enti = uniqueElementsBy(enti, (a, b) => a.id === b.id)
      return enti
    },
    struttureOpzioni () {
      if (!this.asl) return this.allStrutture
      return this.allStrutture.filter(s => s.ente && s.ente.id === this.asl)
    }
  },
  watch: {
    asl: {
      immediate: true,
      handler (newValue, oldValue) {
        // Quando l'ASL cambia dobbiamo annullare la selezione della struttura
        if (newValue !== oldValue) {
          this.scheda.struttura = null
        }
      }
    }
  },
  methods: {
    onHide () {
      this.$modal.hide('modifica-scheda-paziente')
      location.reload()
    },
    debug () {
      console.log('paziente', this.paziente)
      console.log('scheda', this.scheda)
    },
    forceUpdate (event) {
      console.log('forceupdate', this.paziente)
      this.$forceUpdate()
    },
    beforeOpen (event) {
      console.log('beforeOpen', event)
      this.paziente = event.params.paziente
      this.scheda = event.params.scheda
      if (this.scheda) {
        if (this.scheda.usca && this.scheda.usca.id) {
          this.scheda.struttura = this.scheda.usca.id
        }
        if (this.scheda.prescrizioni) {
          for (let i = 0; i < this.scheda.prescrizioni.length; i++) {
            this.prescrizioni[this.scheda.prescrizioni[i].tipo.codice] = { prescrizione: this.scheda.prescrizioni[i].prescrizione }
          }
        }
      } else {
        this.scheda = {
          critica: 'false'
        }
      }
      this.feedback = null
      this.finish = false
    },
    validateScheda () {
      const result = []
      if (!this.scheda.struttura) {
        result.push('Indicare la struttura')
      }
      if (!this.scheda.critica) {
        result.push('Indicare se è critico')
      }
      if (!this.scheda.descrizione) {
        result.push('Compilare il campo "Anamnesi patologica prossima e remota"')
      }
      return result
    },
    inviaScheda () {
      this.loading = true
      this.$http.put(Constants.VISURAMMG_EXTERNAL_URL + 'schede/' + this.scheda.id + '/stato', { stato: 'I' }).then(response => {
        console.log('inviaScheda', response)
        this.finish = true
        this.loading = false
        if (response && response.data && response.data.message) {
          this.feedback = { type: 'success', message: response.data.message }
        } else {
          this.feedback = { type: 'success', message: 'Richista di intervento inviata' }
        }
      }).catch(error => {
        console.log(error)
        this.loading = false
        this.feedback = { type: 'danger', message: 'Si è verificato un errore nel salvataggio dei dati' }
        console.error('saveScheda  Error', error)
      }).then(this.loading = false)
    },
    saveScheda (bozza) {
      console.log('saveScheda', bozza)
      this.feedback = null

      const validateMessageDetail = this.validateScheda()
      if (validateMessageDetail.length === 0) {
        this.scheda.prescrizioni = []
        for (let i = 0; i < this.allTipiPrescrizione.length; i++) {
          if (this.prescrizioni[this.allTipiPrescrizione[i].codice].prescrizione) {
            this.scheda.prescrizioni.push(this.prescrizioni[this.allTipiPrescrizione[i].codice])
          }
        }

        if (bozza) {
          this.scheda.stato = 'B'
        } else {
          this.scheda.stato = 'I'
        }

        this.scheda.soggetto = this.paziente.idSoggetto
        this.loading = true
        this.$http.post(Constants.VISURAMMG_EXTERNAL_URL + 'schede', this.scheda).then(response => {
          console.log('saveScheda', response)
          this.finish = true
          this.loading = false
          if (response && response.data && response.data.message) {
            this.feedback = { type: 'success', message: response.data.message }
          } else {
            this.feedback = { type: 'success', message: 'Richista di intervento salvata' }
          }
        }).catch(error => {
          console.log(error)
          this.loading = false
          this.feedback = { type: 'danger', message: 'Si è verificato un errore nel salvataggio dei dati' }
          console.error('saveScheda  Error', error)
        }).then(this.loading = false)
      } else {
        this.feedback = { type: 'warning', message: 'Si è verificato un errore nel salvataggio dei dati' }
        this.feedback.type = 'warning'
        this.feedback.message = 'Per proseguire è necessario correggere le seguenti anomalie'
        this.feedback.details = validateMessageDetail
      }
    }
  },
  mounted () {
    // strutture
    console.log('loadStrutture')
    this.$http.get(Constants.VISURAMMG_EXTERNAL_URL + 'strutture/usca').then(response => {
      this.allStrutture = response.data
      console.log('getStrutture', response.data)
    }).catch(error => {
      console.log(error)
    }).then(this.loading = false)

    // prescrizione-tipi
    console.log('loadTipiPrescrizione')
    this.$http.get(Constants.VISURAMMG_EXTERNAL_URL + 'prescrizione-tipi').then(response => {
      this.allTipiPrescrizione = response.data
      this.prescrizioni = {}
      for (let i = 0; i < this.allTipiPrescrizione.length; i++) {
        this.prescrizioni[this.allTipiPrescrizione[i].codice] = { tipo: this.allTipiPrescrizione[i].id }
      }
      console.log('getTipiPrescrizione', response.data)
    }).catch(error => {
      console.log(error)
    }).then(this.loading = false)
  }
}
</script>
<style scoped>
    .modal-content{font-size: .8em; display: table; border-spacing:0 16px;padding-bottom: 15px;padding-top:0}
    .modal-title{font-size: 1.6em;padding-bottom: 6px; border-bottom: solid 1px #ccc; margin-bottom: 6px; width:100%;}
    .modal-title small{color: #999; }
    .label-column{width: 15%; vertical-align: middle;}
    .label-column-textarea{width: 15%; vertical-align: top; text-align: right; display: table-cell;}
    .no-desktop{display: none;}
    .form-group-end-section .label-column-textarea {border-bottom: solid 1px #ccc;}
    .toolbar-btn-separator{padding: 0 1em;}
    @media only screen and (max-width: 768px) {
      .no-desktop{display: inline-block;}
      .no-mobile{display: none;}
      .label-column-textarea{text-align: left;}
    }
</style>
