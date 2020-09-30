<template>
  <div class="home">
    <Pazienti />
  </div>
</template>

<script>
// @ is an alias to /src
import Pazienti from '@/components/paziente/Pazienti'

export default {
  name: 'Home',
  components: {
    Pazienti
  }
}
</script>
