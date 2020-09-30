<template>
  <modal name="releasenotes-modal" :adaptive='true' height="auto" :scrollable="true"
    :styles="'display: flex; height: auto;padding: 12px;background-color: transparent;box-shadow: none' " @before-open="beforeOpen">
    <div class='modal-content'>
        <!-- <div class="close">
            &times;
        </div> -->
        <div class='modal-title'>
            <div class='title-text'>Novità della versione {{version}} </div>
            <div  class='title-date' v-if='releasenotes'>{{releasenotes.releasedate}}</div>
        </div>
        <div class='modal-body'>
          <ul v-if='releasenotes'>
            <li class='releasenotes-item' v-for='item in releasenotes.improvements' :key='item.index' >
              <strong>{{item.title}}</strong>
              <span v-html='item.text'></span>
              <i>{{item.notes}}</i>
            </li>
          </ul>
        </div>
        <div class='modal-footer'>
          <div class='release-notes-info-close'>
            <i><small>Per visualizzare nuovamente le novità di questa versione cliccare sul numero di versione a fondo pagina</small></i>
          </div>
          <div class='toolbar-btn'>
            <a href="#" @click.prevent="cancel"  >Chiudi</a>
          </div>

        </div>
    </div>
  </modal>
</template>

<script>
import Releasenotes from '@/util/releasenotes'
import Constants from '@/util/constants'

export default {
  name: 'TheReleasenotes',
  data () {
    return {
      version: Constants.VERSION,
      releasenotes: Releasenotes.Releasenotes[this.version]
    }
  },
  methods: {
    beforeOpen () {
      console.log('releasenoes - modal beforeOpen', Releasenotes.Releasenotes[this.version])
      this.releasenotes = Releasenotes.Releasenotes[this.version]
    },
    cancel () {
      console.log('cancel')
      if (localStorage) {
        localStorage['relasenotes_' + Constants.VERSION + '_readed'] = true
      }
      this.$modal.hide('releasenotes-modal')
    }
  }
}
</script>

<style scoped>
    .modal-title{display: flex ; font-size: 1.4em; padding-bottom: 12px; border-bottom: 1px solid #ccc;}
    .title-text{flex-grow:1;}
    .title-date{color:#bbb; font-size: .8em;}
    .modal-body{padding: 24px 0;}
    .modal-body strong, .modal-body i, .modal-body span{display: block; padding: 4px 0;}
    .modal-footer{padding: 12px 12px 0 128px; border-top: 1px solid #ccc; display: flex;align-content: center;}
    .release-notes-info-close{padding:0  12px; font-size: .7em;}
    .toolbar-btn{padding: .5em 1em;}
    .toolbar-btn a{text-decoration: none; color: #000;}
    .toolbar-btn a:hover{text-decoration: none;}
    @media only screen and (max-width: 768px) {
      ul{padding-left: 12px;}
      .modal-footer{flex-direction: column; padding: 12px;}
      .toolbar-btn{ box-sizing: border-box; width: 100%;height: auto;margin-top: 12px;}
    }
</style>
