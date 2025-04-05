<template>
  <NConfigProvider
    v-if="!isLock"
    :date-locale="dateZhCN"
    :locale="zhCN"
    :theme="getDarkTheme"
    :theme-overrides="getThemeOverrides"
  >
    <AppProvider>
      <RouterView />
    </AppProvider>
  </NConfigProvider>

  <n-watermark
    v-if="showWatermark"
    :font-size="14"
    :height="384"
    :line-height="10"
    :rotate="-20"
    :width="284"
    :x-offset="22"
    :y-offset="100"
    :z-index="9999"
    content="RuoYi-AI"
    cross
    fullscreen
  />
</template>

<script lang="ts" setup>
  import { computed } from 'vue';
  import { darkTheme, dateZhCN, GlobalThemeOverrides, zhCN } from 'naive-ui';
  import { AppProvider } from '@/components/Application';
  import { useScreenLockStore } from '@/store/modules/screenLock.js';
  import { useDesignSettingStore } from '@/store/modules/designSetting';
  import { lighten } from '@/utils';

  const useScreenLock = useScreenLockStore();
  const designStore = useDesignSettingStore();
  const isLock = computed(() => useScreenLock.isLocked);

  /**
   * @type import('naive-ui').GlobalThemeOverrides
   */
  const getThemeOverrides = computed<GlobalThemeOverrides>(() => {
    const appTheme = designStore.appTheme;
    const lightenStr = lighten(designStore.appTheme, 6);
    return {
      common: {
        primaryColor: appTheme,
        primaryColorHover: lightenStr,
        primaryColorPressed: lightenStr,
        primaryColorSuppl: appTheme,
        borderRadius: '7px',
      },
      LoadingBar: {
        colorLoading: appTheme,
      },
    };
  });

  const getDarkTheme = computed(() => (designStore.darkTheme ? darkTheme : undefined));
  const showWatermark = true;
</script>

<style lang="less">
  @import 'styles/index.less';
</style>
