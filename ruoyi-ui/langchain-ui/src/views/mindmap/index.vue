

<script lang="ts" setup>
  import Sider from './components/Sider.vue';
  import MindMap from './components/MindMap.vue';
  import { ref } from 'vue';
  import { useMessage } from 'naive-ui';
  import { genMindMap } from '@/api/agi/chat';
  import { isNullOrWhitespace } from '@/utils/is';
  import { useChatStore } from '@/views/chat/store/useChatStore';

  const ms = useMessage();
  const loading = ref(false);
  const chatStore = useChatStore();
  const genText = ref('');

  async function onGenerate(text: string) {
    if (isNullOrWhitespace(text)) {
      ms.warning('内容为空');
      return;
    }
    loading.value = true;
    try {
      const { message } = await genMindMap({
        message: text,
        modelId: chatStore.modelId,
        modelName: chatStore.modelName,
        modelProvider: chatStore.modelProvider,
      });
      genText.value = message;
    } finally {
      loading.value = false;
    }
  }

  function onRender(text: string) {
    genText.value = text;
  }
</script>

<template>
  <div class="w-full flex h-full">
    <Sider :genText="genText" :loading="loading" @generate="onGenerate" @render="onRender" />

    <MindMap :genText="genText" :loading="loading" />
  </div>
</template>

<style lang="less" scoped></style>
