<template>
  <div class="footer">
    <div class="footer-logoregione">
    </div>
    <div class='space'>&nbsp;</div>
    <div class="footer-logocsi">
    </div>
    <div class='space'>&nbsp;</div>
    <div class='footer-version' @click="showReleasenotesModal()" v-if='version'>Versione {{version}}</div>
    <div class='footer-logo'>
        <TheLogo />
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import TheLogo from '@/components/common/TheLogo'
import Constants from '@/util/constants'

export default {
  name: 'TheFooter',
  components: {
    TheLogo
  },
  data () {
    return {
      version: Constants.VERSION
    }
  },
  methods: {
    showReleasenotesModal: function () {
      console.log('app showReleasenotesModal')
      this.$modal.show('releasenotes-modal')
    }
  }
}
</script>

<style scoped>
.footer{display: flex;padding: 16px 8px; background: #fff;margin-top:1.5rem; background: #006cb4;}
.footer .footer-logo{margin-right: 8px; color: white;}
.footer .footer-logo .logo .pazienti{text-align: right;}
.footer-logoregione{width: 164px; height:50px;background-size: cover;
  background-image:url('../../assets/logo-regione-piemonte.svg');
}
.footer-logocsi{width: 118px;height:50px;background-size: cover;
  background-image:url('../../assets/logo-csi.png');
}
.space{flex-grow: 1;min-height: 36px; }
.footer-version{color: white; padding: 1.4em 2em 0 0; }
.footer-version:hover{cursor: pointer;}
 @media only screen and (max-width: 768px) {
   .footer{flex-direction: column; text-align: left;}

 }
</style>
