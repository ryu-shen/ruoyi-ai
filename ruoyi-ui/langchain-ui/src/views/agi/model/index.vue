

<script lang="ts" setup>
  import { ref } from 'vue';
  import SvgIcon from '@/components/SvgIcon/index.vue';
  import ChatProvider from './components/chat/index.vue';
  import EmbedProvider from './components/embedding/index.vue';
  import ImageProvider from './components/image/index.vue';

  const active = ref('1');
</script>

<template>
  <div class="h-full overflow-y-auto">
    <n-card :bordered="false" class="mt-2">
      <n-tabs v-model:value="active" class="flex items-center">
        <n-tab name="1">
          <SvgIcon class="text-lg" icon="lets-icons:chat" />
          <span class="pl-2 font-bold">Chat模型供应商</span>
        </n-tab>
        <n-tab name="2">
          <SvgIcon class="text-lg" icon="ph:database" />
          <span class="pl-2 font-bold">Embedding向量模型</span>
        </n-tab>
        <n-tab name="3">
          <SvgIcon class="text-lg" icon="ph:image" />
          <span class="pl-2 font-bold">Image文生图模型</span>
        </n-tab>
      </n-tabs>
      <n-divider />

      <ChatProvider v-if="active === '1'" />
      <EmbedProvider v-if="active === '2'" />
      <ImageProvider v-if="active === '3'" />
    </n-card>
  </div>
</template>

<style lang="less" scoped>
  ::v-deep(.n-menu .n-menu-item) {
    height: 40px !important;
  }
</style>
