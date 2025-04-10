<script lang="ts" setup>
  import { computed, ref } from 'vue';
  import { useMessage } from 'naive-ui';
  import TextComponent from './TextComponent.vue';
  import SvgIcon from '@/components/SvgIcon/index.vue';
  import { useIconRender } from '../store/useIconRender';
  import { copyToClip } from '@/utils/copy';

  interface Props {
    dateTime?: string;
    text?: string;
    inversion?: boolean;
    error?: boolean;
    loading?: boolean;
  }

  interface Emit {
    (ev: 'delete'): void;
  }

  const props = defineProps<Props>();
  const emit = defineEmits<Emit>();
  const isHover = ref(false);
  const { iconRender } = useIconRender();
  const message = useMessage();
  const textRef = ref<HTMLElement>();
  const asRawText = ref(props.inversion);
  const messageRef = ref<HTMLElement>();
  const options = computed(() => {
    const common = [
      {
        label: '复制',
        key: 'copyText',
        icon: iconRender({ icon: 'ri:file-copy-2-line' }),
      },
    ];

    if (!props.inversion) {
      common.push({
        label: asRawText.value ? '预览' : '显示原文',
        key: 'toggleRenderType',
        icon: iconRender({ icon: asRawText.value ? 'ic:outline-code-off' : 'ic:outline-code' }),
      });
    }

    return common;
  });

  function handleSelect(key: 'copyText' | 'delete' | 'toggleRenderType') {
    switch (key) {
      case 'copyText':
        handleCopy();
        return;
      case 'toggleRenderType':
        asRawText.value = !asRawText.value;
        return;
      case 'delete':
        emit('delete');
    }
  }

  async function handleCopy() {
    try {
      await copyToClip(props.text || '');
      message.success('复制成功');
    } catch {
      message.error('复制失败');
    }
  }
</script>

<template>
  <div
    ref="messageRef"
    :class="[{ 'flex-row-reverse': inversion }]"
    class="flex w-full overflow-hidden"
  >
    <div
      :class="[inversion ? 'ml-2' : 'mr-2']"
      class="flex items-center justify-center bg-gray-200 flex-shrink-0 h-8 overflow-hidden rounded-full basis-8"
    >
      <SvgIcon v-if="inversion" icon="solar:user-broken" />
      <SvgIcon v-else icon="mingcute:ai-line" />
    </div>
    <div :class="[inversion ? 'items-end' : 'items-start']" class="overflow-hidden text-sm">
      <p :class="[inversion ? 'text-right' : 'text-left']" class="text-xs text-[#b4bbc4]">
        {{ dateTime }}
      </p>
      <div
        @mouseover="isHover = true"
        @mouseleave="isHover = false"
        :class="[inversion ? 'flex-row-reverse' : 'flex-row']"
        class="flex items-end gap-1 mt-2 transition-all"
      >
        <TextComponent
          ref="textRef"
          :as-raw-text="asRawText"
          :error="error"
          :inversion="inversion"
          :loading="loading"
          :text="text"
        />
        <div class="flex flex-col transition-all w-[45px]">
          <n-space v-if="isHover" class="transition-all gap-1.5 flex-nowrap justify-end">
            <n-popover v-for="item in options" :key="item" class="custom-popover">
              <template #trigger>
                <button
                  @click="handleSelect(item.key as any)"
                  class="transition text-neutral-400 hover:text-neutral-800"
                >
                  <component :is="item.icon" />
                </button>
              </template>
              {{ item.label }}
            </n-popover>
          </n-space>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="less" scoped></style>
