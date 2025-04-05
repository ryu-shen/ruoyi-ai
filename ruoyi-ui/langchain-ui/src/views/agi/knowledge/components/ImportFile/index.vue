

<script lang="ts" setup>
  import DocImport from '@/views/agi/knowledge/components/ImportFile/components/DocImport.vue';

  interface Props {
    data?: any;
  }
  defineProps<Props>();
</script>

<template>
  <div v-if="data" class="flex flex-col gap-3">
    <DocImport />
  </div>
</template>
<style lang="less" scoped></style>
