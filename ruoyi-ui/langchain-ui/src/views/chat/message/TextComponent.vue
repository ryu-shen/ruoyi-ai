<script lang="ts" setup>
  import { computed, onMounted, onUnmounted, onUpdated, ref } from 'vue';
  import MarkdownIt from 'markdown-it';
  import mdKatex from '@traptitech/markdown-it-katex';
  import mila from 'markdown-it-link-attributes';
  import hljs from 'highlight.js';
  import { useBasicLayout } from '../store/useBasicLayout';
  import { copyToClip } from '@/utils/copy';

  interface Props {
    inversion?: boolean;
    error?: boolean;
    text?: string;
    loading?: boolean;
    asRawText?: boolean;
  }

  const props = defineProps<Props>();

  const { isMobile } = useBasicLayout();

  const textRef = ref<HTMLElement>();

  const mdi = new MarkdownIt({
    html: false,
    linkify: true,
    highlight(code, language) {
      const validLang = !!(language && hljs.getLanguage(language));
      if (validLang) {
        const lang = language ?? '';
        return highlightBlock(hljs.highlight(code, { language: lang }).value, lang);
      }
      return highlightBlock(hljs.highlightAuto(code).value, '');
    },
  });

  mdi.use(mila, { attrs: { target: '_blank', rel: 'noopener' } });
  mdi.use(mdKatex, { blockClass: 'katexmath-block rounded-md p-[10px]', errorColor: ' #cc0000' });

  const wrapClass = computed(() => {
    return [
      'text-wrap',
      'rounded-md',
      isMobile.value ? 'p-2' : 'px-3 py-2',
      props.inversion ? 'bg-[#70c0e829]' : 'bg-[#f4f6f8]',
      props.inversion ? 'message-request' : 'message-reply',
      { 'text-red-500': props.error },
    ];
  });

  const text = computed(() => {
    const value = props.text ?? '';
    if (!props.asRawText) {
      return mdi.render(value);
    }
    return value;
  });

  function highlightBlock(str: string, lang?: string) {
    return `<pre class="code-block-wrapper"><div class="code-block-header"><span class="code-block-header__lang">${lang}</span><span class="code-block-header__copy">复制</span></div><code class="hljs code-block-body ${lang}">${str}</code></pre>`;
  }

  function addCopyEvents() {
    if (textRef.value) {
      const copyBtn = textRef.value.querySelectorAll('.code-block-header__copy');
      copyBtn.forEach((btn) => {
        btn.addEventListener('click', () => {
          const code = btn.parentElement?.nextElementSibling?.textContent;
          if (code) {
            copyToClip(code).then(() => {
              btn.textContent = '复制成功';
              setTimeout(() => {
                btn.textContent = '复制代码';
              }, 1000);
            });
          }
        });
      });
    }
  }

  function removeCopyEvents() {
    if (textRef.value) {
      const copyBtn = textRef.value.querySelectorAll('.code-block-header__copy');
      copyBtn.forEach((btn) => {
        btn.removeEventListener('click', () => {});
      });
    }
  }

  onMounted(() => {
    addCopyEvents();
  });

  onUpdated(() => {
    addCopyEvents();
  });

  onUnmounted(() => {
    removeCopyEvents();
  });
</script>

<template>
  <div :class="wrapClass" class="text-black">
    <div ref="textRef" class="leading-relaxed break-words">
      <div v-if="!inversion">
        <div v-if="!asRawText" class="markdown-body" v-html="text"></div>
        <div v-else class="whitespace-pre-wrap" v-text="text"></div>
      </div>
      <div v-else class="whitespace-pre-wrap" v-text="text"></div>
      <template v-if="loading && !text">
        <span class="w-[4px] h-[20px] block animate-blink"></span>
      </template>
    </div>
  </div>
</template>

<style lang="less">
  @import 'styles';
</style>
