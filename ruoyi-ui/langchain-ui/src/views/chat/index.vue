<script lang="ts" setup>
  import { onMounted, ref } from 'vue';
  import Chat from '@/views/chat/Chat.vue';
  import { getMessages } from '@/api/agi/chat';
  import { useChatStore } from '@/views/chat/store/useChatStore';
  import { useUserStore } from '@/store/modules/user';
  import Header from '@/views/chat/Header.vue';

  const loading = ref(true);
  const chatStore = useChatStore();
  const userStore = useUserStore();

  onMounted(async () => {
    await fetch();
  });

  async function fetch() {
    loading.value = true;
    chatStore.conversationId = userStore.info.id;
    chatStore.messages = await getMessages(userStore.info.id);
    loading.value = false;
  }
</script>

<template>
  <n-card class="p-4 pt-1 w-full h-full">
    <Header title="AI聊天助手" @reload="fetch" />
    <div class="flex flex-col w-full overflow-hidden" style="height: calc(100vh - 180px)">
      <main ref="contentRef" class="flex-1 overflow-hidden overflow-y-auto">
        <Chat />
      </main>
    </div>
  </n-card>
</template>

<style lang="less" scoped>
  ::v-deep(.n-tabs.n-tabs--top .n-tab-pane) {
    padding: 0 !important;
  }
</style>
