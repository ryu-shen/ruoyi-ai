

<script lang="ts" setup>
  import AiReqChart from './AiReqChart.vue';
  import AiTokenChart from './AiTokenChart.vue';
</script>

<template>
  <div class="mt-4 w-full">
    <n-card :bordered="false" content-style="padding: 0;">
      <n-tabs :tabs-padding="20" pane-style="padding: 10px" size="large" type="line">
        <n-tab-pane name="Token消耗量统计">
          <AiTokenChart />
        </n-tab-pane>
        <n-tab-pane name="AI请求量统计">
          <AiReqChart />
        </n-tab-pane>
      </n-tabs>
    </n-card>
  </div>
</template>
