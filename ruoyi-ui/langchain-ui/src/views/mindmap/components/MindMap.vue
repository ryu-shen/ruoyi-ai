

<script lang="ts" setup>
  import SvgIcon from '@/components/SvgIcon/index.vue';
  import { Transformer } from 'markmap-lib';
  import { Markmap } from 'markmap-view';
  import { onMounted, watch } from 'vue';
  import { downloadPdf, downloadPng, downloadSvg } from '@/utils/downloadFile';

  const props = defineProps<{
    genText: string;
  }>();

  let instance: Markmap | null = null;
  onMounted(() => {
    const el = document.getElementById('mindmap') as any;
    instance = Markmap.create(el);
  });

  watch(
    () => props.genText,
    (val) => {
      const transformer = new Transformer();
      const { root } = transformer.transform(val);
      instance?.setData(root);
      instance?.fit();
    }
  );

  function onZoomIn() {
    instance?.rescale(2);
  }
  function onZoomOut() {
    instance?.rescale(0.5);
  }
  function onZoomFill() {
    instance?.fit();
  }

  function downPng() {
    downloadPng('mindmap-view', 'mindmap-shot');
  }
  function downSvg() {
    downloadSvg('mindmap-view', 'mindmap-shot');
  }
  function downPdf() {
    downloadPdf('mindmap-view', 'mindmap-shot');
  }
</script>

<template>
  <div :class="genText == '' ? 'overflow-hidden' : ''" class="dot-bg w-full h-full relative">
    <div class="top-4 z-10 flex left-2 absolute flex-wrap justify-center gap-2">
      <n-button text @click="onZoomIn">
        <SvgIcon class="text-2xl" icon="basil:zoom-in-outline" />
      </n-button>
      <n-button text @click="onZoomOut">
        <SvgIcon class="text-2xl" icon="basil:zoom-out-outline" />
      </n-button>
      <n-button text @click="onZoomFill">
        <SvgIcon class="text-2xl" icon="fluent:full-screen-zoom-24-filled" />
      </n-button>
      <n-button round size="small" @click="downPng">
        <template #icon>
          <SvgIcon class="text-lg" icon="material-symbols:download" />
        </template>
        PNG
      </n-button>
      <n-button round size="small" @click="downSvg">
        <template #icon>
          <SvgIcon class="text-lg" icon="material-symbols:download" />
        </template>
        SVG
      </n-button>
      <n-button round size="small" @click="downPdf">
        <template #icon>
          <SvgIcon class="text-lg" icon="material-symbols:download" />
        </template>
        PDF
      </n-button>
    </div>

    <div v-if="genText == ''" class="h-full w-full flex flex-col justify-center items-center gap-3">
      <SvgIcon class="text-6xl" icon="ri:mind-map" />
      <div class="text-2xl font-bold">思维导图</div>
      <div class="text-gray-400">在左侧输入内容点击生成思维导图</div>
    </div>

    <div id="mindmap-view" class="h-full w-full">
      <svg id="mindmap" class="h-full w-full" />
    </div>
  </div>
</template>

<style lang="less" scoped></style>
