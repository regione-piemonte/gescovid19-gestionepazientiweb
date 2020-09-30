<template>
  <div class="header">
    <div class="header-logoregione">
    </div>
    <div class='header-logo'>
        <TheLogo />
    </div>
    <div class='header-user'>
        <TheUser />
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import TheLogo from '@/components/common/TheLogo'
import TheUser from '@/components/common/TheUser'

export default {
  name: 'TheHeader',
  components: {
    TheUser,
    TheLogo
  }
}
</script>

<style scoped>
.header{display: flex;padding: 8px; background: #006cb4;color: white; margin-bottom: 1.5rem;
  box-shadow: 0 1px 8px rgba(0,0,0,.2), 0 3px 4px rgba(0,0,0,.14), 0 3px 3px -2px rgba(0,0,0,.12);}
.header .header-logo{margin-right: 8px; color: white;}
.header .header-logo .logo{ color: white; text-align: left;}
.header .header-user{text-align: right;flex-grow: 1;}
.header-logoregione{
  width:0px;
  height:0px;
  }
  @media only screen and (max-width: 768px) {
    .header{margin-bottom: .5rem;}
  }
</style>
