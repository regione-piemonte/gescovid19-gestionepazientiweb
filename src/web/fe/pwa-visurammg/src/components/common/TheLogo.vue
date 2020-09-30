<template>
   <div class="logo">
     <span class="visura">Visura pazienti e tamponi MMG</span>
     <span class="pazienti">Piattaforma Covid 19  Piemonte</span>
     </div>
</template>
<script>
export default {
  name: 'TheLogo',
  props: {
    msg: String
  }
}
</script>
<style scoped>
.logo{letter-spacing: 0px; font-size: 24px; font-family: 'Titillium Web',  sans-serif; color: #fff;}
.logo .visura{font-weight:700;display: block;line-height: 0.9em;}
.logo .pazienti{font-weight:300;display: block; font-size: .6em; margin-top: 0px;text-align: left;}
</style>
