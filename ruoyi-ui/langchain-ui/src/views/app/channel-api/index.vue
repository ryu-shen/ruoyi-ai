

<script lang="ts" setup>
  import Docs from './components/docs.vue';
  import ApiTable from '@/views/app/ApiTable.vue';
  import { CHANNEL } from '@/views/app/columns';
</script>

<template>
  <div class="w-full my-3 pb-8 flex items-start justify-start gap-2 h-full">
    <div class="bg-white p-4 rounded w-4/5 h-full">
      <ApiTable :channel="CHANNEL.API" />
    </div>

    <Docs class="w-full" />
  </div>
</template>

<style lang="less" scoped></style>
