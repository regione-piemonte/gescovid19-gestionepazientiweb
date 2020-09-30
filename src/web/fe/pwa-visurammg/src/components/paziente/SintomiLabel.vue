<template>
    <span>
      {{label}}
    </span>
</template>

<script>
export default {
  name: 'SintomiLabel',
  props: {
    sintomi: { type: Array, required: false, default: () => [] }
  },
  computed: {
    label () {
      return (
        this.sintomi
          .map(s => {
            let result = s.nome

            if (s.misurabile && s.valore) {
              result = `${s.nome} (${s.valore} ${s.unita_misura})`
            }

            return result
          })
          .join(', ') || '-'
      )
    }
  }
}
</script>

<style scoped>

</style>
