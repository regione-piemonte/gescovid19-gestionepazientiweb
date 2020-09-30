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
      <div class='modal-title'>
        Il tuo profilo
      </div>

      <!-- BODY -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <form class='form' v-if='!finish'>
        <dl>
          <dt>Cognome</dt>
          <dd>{{utenteCognome | empty}}</dd>

          <dt>Nome</dt>
          <dd>{{utenteNome | empty}}</dd>
        </dl>

        <div class='form-section'>
          <!-- TELEFONO -->
          <!-- ----------------------------------------------------------------------------------------------------- -->
          <div>
            <div class="py-md">
              <label class='label-column control-label'>
                <strong>Telefono</strong>
              </label>
            </div>

            <div class="py-md">
              <input type='tel' v-model='telefono' placeholder="Telefono">
            </div>
          </div>

          <!-- EMAIL -->
          <!-- ----------------------------------------------------------------------------------------------------- -->
          <div>
            <div class="py-md">
              <label class='label-column control-label'>
                <strong>Email</strong>
              </label>
            </div>

            <div class="py-md">
              <input type='email' v-model='email' placeholder="Email">
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
import { faCircleNotch } from '@fortawesome/free-solid-svg-icons'
import { aggiornaContattiUtente, getCurrentUserNew } from '../../util/api'

export default {
  name: 'UtenteProfiloModal',
  data () {
    return {
      iconSpinner: faCircleNotch,
      nome: 'profilo-utente',
      utente: null,
      telefono: '',
      email: '',
      finish: false,
      loading: false,
      feedback: null
    }
  },
  computed: {
    utenteNome () {
      return this.utente && this.utente.nome
    },
    utenteCognome () {
      return this.utente && this.utente.cognome
    }
  },
  methods: {
    async beforeOpen (event) {
      console.log('beforeOpen', event)
      this.loading = true

      try {
        const { data: utente } = await getCurrentUserNew()
        this.utente = utente
        this.telefono = (this.utente && this.utente.telefono) || ''
        this.email = (this.utente && this.utente.email) || ''
      } catch (err) {
        console.error(err)
      }

      this.loading = false
    },
    nascondiERicarica () {
      this.$modal.hide(this.nome)
      location.reload()
    },
    async salva () {
      this.feedback = null

      const codiceFiscale = this.utente && this.utente.cfUtente
      const payload = {
        nome: (this.utente && this.utente.nome) || '',
        cognome: (this.utente && this.utente.cognome) || '',
        telefono: this.telefono,
        email: this.email
      }

      console.log(JSON.stringify(payload, null, 4))

      this.loading = true

      try {
        await aggiornaContattiUtente(codiceFiscale, payload)
        this.finish = true
        this.feedback = {
          tipo: 'success',
          testo: 'Profilo aggiornato con successo'
        }
      } catch (err) {
        console.error(err)
        this.feedback = {
          tipo: 'danger',
          testo: "Si è verificato durante l'aggiornamento del profilo"
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
