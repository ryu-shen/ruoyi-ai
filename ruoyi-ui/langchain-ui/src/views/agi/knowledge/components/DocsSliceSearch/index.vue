

<script lang="ts" setup>
  import { ref } from 'vue';
  import SvgIcon from '@/components/SvgIcon/index.vue';
  import { embeddingSearch } from '@/api/agi/embedding';
  import router from '@/router';
  import { useMessage } from 'naive-ui';

  const content = ref('');
  const loading = ref(false);
  const list = ref<any>([]);
  const ms = useMessage();

  async function onSearch() {
    if (content.value === '') {
      list.value = [];
      ms.warning('请先输入搜索内容');
      return;
    }
    loading.value = true;
    list.value = await embeddingSearch({
      content: content.value,
      knowledgeId: router.currentRoute.value.params.id,
    }).finally(() => {
      loading.value = false;
    });
  }
</script>

<template>
  <n-card class="h-full w-full">
    <div class="flex h-full gap-4">
      <div class="w-1/3 flex flex-col gap-3">
        <n-button :loading="loading" secondary type="primary" @click="onSearch">向量搜索</n-button>
        <n-input
          v-model:value="content"
          placeholder="请输入关键词查询向量文本"
          rows="10"
          type="textarea"
        />
      </div>
      <div class="w-full">
        <n-spin :show="loading">
          <div class="grid grid-cols-3 gap-4">
            <n-card
              v-for="item in list"
              :key="item.index"
              :bordered="false"
              class="rounded-lg cursor-pointer"
              embedded
              hoverable
            >
              <template #header>
                <n-skeleton v-if="loading" text width="60%" />
                <div v-else class="flex items-center gap-1">
                  <SvgIcon class="text-3xl" icon="flat-color-icons:document" />
                  <n-ellipsis> {{ item.docsName }} </n-ellipsis>
                </div>
              </template>
              <n-scrollbar style="max-height: 200px">
                {{ item.text }}
              </n-scrollbar>
            </n-card>
          </div>
          <n-empty v-if="list.length === 0" class="my-4" />
        </n-spin>
      </div>
    </div>
  </n-card>
</template>

<style lang="less" scoped></style>
