<script lang="ts" setup>
  import SvgIcon from '@/components/SvgIcon/index.vue';
  import { useChatStore } from '@/views/chat/store/useChatStore';
  import { useDialog, useMessage } from 'naive-ui';
  import { clean } from '@/api/agi/chat';
  import ModelSelect from '@/views/common/ModelSelect.vue';

  defineProps<{
    title: string;
  }>();
  const emits = defineEmits(['reload']);
  const dialog = useDialog();
  const ms = useMessage();
  const chatStore = useChatStore();

  function handleClear() {
    if (chatStore.conversationId == null) {
      return;
    }
    dialog.warning({
      title: '清除聊天',
      content: '确认清除聊天',
      positiveText: '是',
      negativeText: '否',
      onPositiveClick: async () => {
        await clean(chatStore.conversationId);
        emits('reload');
        ms.success('聊天记录清除成功');
      },
    });
  }
</script>

<template>
  <div class="mb-3 flex flex-wrap justify-between items-center">
    <div class="font-bold flex justify-center items-center flex-wrap gap-2">
      <SvgIcon class="text-lg" icon="ion:sparkles-outline" />
      <span>{{ title }}</span>
    </div>
    <n-space align="center">
      <ModelSelect :id="chatStore.modelId" class="w-auto" style="min-width: 180px" />

      <n-button secondary size="small" type="warning" @click="handleClear">
        <template #icon>
          <SvgIcon class="text-[14px]" icon="fluent:delete-12-regular" />
        </template>
        清空聊天
      </n-button>
    </n-space>
  </div>
</template>

<style lang="less" scoped></style>
