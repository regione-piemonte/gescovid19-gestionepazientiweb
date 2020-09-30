<template>
  <modal name="news-modal" :adaptive='true' height="auto" :scrollable="true"
    :styles="'display: flex; height: auto;padding: 12px;background-color: transparent;box-shadow: none' " @before-open="beforeOpen">
    <div class='modal-content' v-if='news' >
        <!-- <div class="close">
            &times;
        </div> -->
        <div class='modal-title'>
            <div class='title-text'>Comunicazione #{{news.numero}}</div>
        </div>
        <div class='modal-body'>
          <ul v-if='news'>
            <li class='news-item' v-for='item in news.items' :key='item.index' >
              <span v-html='item'></span>
              <!--
              <strong>{{item.title}}</strong>
              <span>{{item.text}}</span>
              <a v-href='item.linkurl' v-if='item.linkurl'>{{item.linklabel}}</a>
              -->
            </li>
          </ul>
        </div>
        <div class='modal-footer'>
          <div class='news-info-close'>
            <label>
              <input type='checkbox' class="form-check-input"  v-model='hidenews'>
              <span>Non visualizzare più questa comunicazione</span>
            </label>
          </div>
          <div class='toolbar-btn'>
            <a href="#" @click.prevent="cancel"  >Chiudi</a>
          </div>

        </div>
    </div>
  </modal>
</template>

<script>
import Constants from '@/util/constants'

export default {
  name: 'TheNews',
  data () {
    return {
      version: Constants.VERSION,
      news: null,
      hidenews: false
    }
  },
  methods: {
    beforeOpen (event) {
      console.log('news - modal beforeOpen', event)
      this.news = event.params.news
    },
    cancel () {
      console.log('cancel')
      if (localStorage && this.hidenews) {
        localStorage['news_' + this.news.numero + '_readed'] = true
      }
      this.$modal.hide('news-modal')
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
    .modal-footer{padding: 12px 12px 0 12px; border-top: 1px solid #ccc; display: flex;align-content: center; justify-content: flex-end;}
    .news-info-close{padding:6px  12px 0 12px; }
    .news-info-close label{display: block;}
    .toolbar-btn{padding: .5em 1em;}
    .toolbar-btn a{text-decoration: none; color: #000;}
    .toolbar-btn a:hover{text-decoration: none;}
    @media only screen and (max-width: 768px) {
      ul{padding-left: 12px;}
      .modal-footer{flex-direction: column; padding: 12px;}
      .toolbar-btn{ box-sizing: border-box; width: 100%;height: auto;margin-top: 12px;}
    }
</style>
