<template>
  <modal
    :name="nome"
    adaptive
    scrollable
    height="auto"
    :styles="'display: flex;padding: 12px;background-color: transparent;box-shadow: none' "
    @before-open="beforeOpen"
  >
    <div class='modal-content'>
      <div class='modal-title pa-md'>
        Dettaglio intervento
      </div>

      <dl>
        <dt>Data intervento</dt>
        <dd>{{data | formatDateTime}}</dd>

        <dt>Medico</dt>
        <dd>{{medicoNome | empty}}</dd>
        <template v-if="medicoTelefono">
          <dd>
            <a :href="'tel:' + medicoTelefono" class="no-decoration">
              {{medicoTelefono}}
            </a>
          </dd>
        </template>

        <dt>Tipo intervento</dt>
        <dd>{{tipo | empty}}</dd>

        <dt>Azione intrapresa</dt>
        <dd>{{azione | empty}}</dd>

        <dt>Descrizione azione intrapresa</dt>
        <dd>{{azioneDescrizione | empty}}</dd>

        <dt>Descrizione</dt>
        <dd>{{descrizione | empty}}</dd>

        <template v-for="prescrizione in prescrizioni">
          <dt :key="'t-' + prescrizione.id">{{prescrizione.tipo.nome}}</dt>
          <dd :key="'p-' + prescrizione.id">{{prescrizione.prescrizione | empty}}</dd>
        </template>
      </dl>

      <!-- FOOTER -->
      <!-- --------------------------------------------------------------------------------------------------------- -->
      <div class='modal-footer pa-md'>
        <div class='toolbar-right'>
          <a href @click.prevent="$modal.hide(nome)" class='btn btn-default'>Chiudi</a>
        </div>
      </div>
    </div>
  </modal>
</template>

<script>
export default {
  name: 'SchedaDettaglioInterventoItemModal',
  props: {
    nome: { type: String, required: false, default: 'intervento-prescrizioni-modal' }
  },
  data () {
    return {
      intervento: null
    }
  },
  computed: {
    data () {
      return this.intervento && this.intervento.data
    },
    tipo () {
      return this.intervento && this.intervento.tipo && this.intervento.tipo.nome
    },
    azione () {
      return this.intervento && this.intervento.azione && this.intervento.azione.nome
    },
    azioneDescrizione () {
      return this.intervento && this.intervento.azione_descrizione
    },
    descrizione () {
      return this.intervento && this.intervento.descrizione
    },
    prescrizioni () {
      return (this.intervento && this.intervento.prescrizioni) || []
    },
    medico () {
      return this.intervento && this.intervento.medico_responsabile
    },
    medicoNome () {
      const medico = this.medico
      const nome = medico && medico.nome
      const cognome = medico && medico.cognome
      return [cognome, nome].join(' ').trim()
    },
    medicoTelefono () {
      return this.medico && this.medico.telefono
    }
  },
  methods: {
    beforeOpen (event) {
      console.log('beforeOpen', event)
      this.intervento = event.params.intervento
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

  @media only screen and (max-width: 768px) {
    .modal-title{font-size: 1.1em; padding-right: 32px; width: auto; line-height: 1.4em;}
    .modal-content{padding:0; display: block;}
    dt, dd {font-size: 12px;}
    .modal-footer {justify-content: center};
    .modal-footer .btn{display: block;}
  }
</style>
