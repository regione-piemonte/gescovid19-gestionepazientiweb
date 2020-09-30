<template>
  <modal
    :name="nome"
    :adaptive='true'
    height="auto"
    :scrollable="true"
    :styles="'display: flex;padding: 12px;background-color: transparent;box-shadow: none' "
    @before-open="beforeOpen"
  >
    <div class='modal-content'>
      <div class="close" @click="$modal.hide(nome)">
        &times;
      </div>

      <div class='modal-title pa-md'>
        Nuovo monitoraggio per {{soggettoNome | empty}}
      </div>

      <!-- BODY -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <form class='form' v-if='!finish'>
        <div class='form-section'>
          <!-- DATA -->
          <!-- ----------------------------------------------------------------------------------------------------- -->
          <div>
            <div class="py-md">
              <label class='label-column control-label'>
                <strong>Data</strong>
              </label>
            </div>

            <div class="py-md">
              <input type='date' v-model='data'>
              <input type="time" v-model='orario' class="mx-md">
            </div>
          </div>

          <!-- SINTOMI -->
          <!-- ----------------------------------------------------------------------------------------------------- -->
          <div>
            <div class="py-md">
              <label class='label-column control-label' for='sintomi'>
                <strong>Sintomi</strong>
              </label>
            </div>

            <div class="py-md">
              <div v-for="sintomo in sintomiInputs" :key="sintomo.id" class='py-sm' style="display: flex; justify-content: space-between">
                <label>
                  <input type='checkbox' v-model='sintomiSelezionati' :value='sintomo' name='sintomi'/>
                  <span>{{sintomo.nome}}</span>
                </label>

                <template v-if="sintomo.misurabile && sintomoSelezionato(sintomo)">
                  <div>
                    <input type='number' v-model.number='sintomo.valore' min="0" step=".1">
                    {{sintomo.unita_misura}}
                  </div>
                </template>
              </div>
            </div>
          </div>

          <!-- ALTRI SINTOMI -->
          <!-- ----------------------------------------------------------------------------------------------------- -->
          <div>
            <div class="py-md">
              <label class='label-column-textarea control-label' for='altri_sintomi'>
                <strong>Altri sintomi</strong>
              </label>
            </div>

            <div class="py-md">
                <textarea
                  v-model='altriSintomi'
                  rows='3'
                  name='altri_sintomi'
                  style="width: 90%"
                >
                </textarea>
            </div>
          </div>

        </div>
      </form>

      <!-- FEEDBACK -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <div class='main-feedback' v-if='feedback'>
        <div class='alert' v-bind:class='feedback.tipo'>
          {{feedback.testo}}
          <ul v-if="feedback.messaggi">
            <li v-for="(messaggio, index) in feedback.messaggi" :key="index">{{messaggio}}</li>
          </ul>
        </div>
      </div>

      <!-- FOOTER -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <div class='modal-footer '>
        <div class='toolbar-left' v-if='!finish'>
          <a href @click.prevent="$modal.hide(nome)" class='btn btn-default'>Annulla</a>
        </div>

        <div class='toolbar-right' v-if='!finish'>
          <a href='#' @click.prevent='salva' class='btn btn-primary' :disable="loading">Salva</a>
          <font-awesome-icon :icon="iconSpinner" class='fa-spin' v-if='loading'/>
        </div>

        <div class='toolbar-right' v-if='finish'>
          <a href @click.prevent="nascondiERicarica" class='btn btn-default'>Chiudi</a>
        </div>
      </div>
    </div>
  </modal>
</template>

<script>
import Constants from '@/util/constants'
import moment from 'moment'
import { faCircleNotch } from '@fortawesome/free-solid-svg-icons'

export default {
  name: 'DiarioPaginaNuovaModal',
  data () {
    return {
      iconSpinner: faCircleNotch,
      nome: 'diario-pagina-nuova',
      soggetto_: null,
      sintomi: [],
      sintomiInputs: [],
      data: null,
      orario: null,
      sintomiSelezionati: [],
      altriSintomi: '',
      finish: false,
      loading: false,
      feedback: null
    }
  },
  computed: {
    soggetto () {
      return this.soggetto_ || {}
    },
    soggettoId () {
      return this.soggetto.idSoggetto
    },
    soggettoNome () {
      const nome = this.soggetto.nome || ''
      const cognome = this.soggetto.cognome || ''
      if (!nome && !cognome) return ''
      return [cognome, nome].join(' ')
    },
    sintomiOrdinati () {
      const result = [...this.sintomi]

      result.sort((a, b) => {
        return a.nome > b.nome ? 1 : -1
      })

      return result
    }
  },
  async created () {
    try {
      const { data } = await this.$http.get(Constants.VISURAMMG_EXTERNAL_URL + 'sintomi')
      this.sintomi = data
      this.sintomiInputs = this.sintomi.map(s => {
        return {
          ...s,
          note: '',
          valore: 0
        }
      })

      this.sintomiInputs.sort((a, b) => {
        return a.nome > b.nome ? 1 : -1
      })
    } catch (err) {
      console.error(err)
    }
  },
  methods: {
    beforeOpen (event) {
      console.log('beforeOpen', event)
      this.soggetto_ = event.params.soggetto
      const now = moment()
      this.data = now.format('YYYY-MM-DD')
      this.orario = now.format('HH:mm')
    },
    sintomoSelezionato (sintomo) {
      return this.sintomiSelezionati.find(s => s.id === sintomo.id)
    },
    nascondiERicarica () {
      this.$modal.hide(this.nome)
      location.reload()
    },
    async salva () {
      const now = moment()
      const nowData = now.format('YYYY-MM-DD')
      const nowOrario = now.format('HH:mm')

      this.feedback = null
      const messaggi = []

      if (!this.data || !this.orario) {
        messaggi.push('Data ed orario sono campi obbligatori')
      } else if (this.data > nowData || (this.data === nowData && this.orario > nowOrario)) {
        messaggi.push('Non puoi impostare una data nel futuro')
      }

      if (this.sintomiSelezionati.length <= 0) {
        messaggi.push('Seleziona alemeno un sintomo prima di salvare il monitoraggio')
      }

      const misurazioneObbligatoria = this.sintomiSelezionati.some(s => s.misurabile && !s.valore)
      if (misurazioneObbligatoria) {
        messaggi.push('Aggiungi le misurazione necessarie nei sintomi')
      }

      if (messaggi.length > 0) {
        this.feedback = {
          tipo: 'warning',
          testo: 'Si è verificato un errore nel salvataggio dei dati',
          messaggi
        }

        return
      }

      let datetime = Date.parse(`${this.data}T${this.orario}`)
      datetime = new Date(datetime).toISOString()

      const payload = {
        data: datetime,
        soggetto: this.soggettoId,
        note: this.altriSintomi,
        intervento: null,
        sintomi: this.sintomiSelezionati
      }

      console.log(JSON.stringify(payload, null, 4))

      this.loading = true

      try {
        const url = Constants.VISURAMMG_EXTERNAL_URL + 'diario-dettagli'
        await this.$http.post(url, payload)
        this.finish = true
        this.feedback = {
          tipo: 'success',
          testo: 'Monitoraggio aggiunto con successo'
        }
      } catch (err) {
        console.error(err)
        this.feedback = {
          tipo: 'danger',
          testo: 'Si è verificato un errore nel salvataggio dei dati'
        }
      }

      this.loading = false
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
