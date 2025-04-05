<script lang="ts" setup>
  import { onMounted, ref, toRaw } from 'vue';
  import { list } from '@/api/agi/app';

  const props = defineProps<{
    id: any;
  }>();
  const emit = defineEmits(['update']);
  const options = ref([]);
  const appId = ref('');

  onMounted(async () => {
    options.value = await list({});
    appId.value = props.id;
  });

  function onUpdate(val: any, opt) {
    const obj = toRaw(opt);
    if (obj == null) {
      emit('update', {
        id: '',
      });
    } else {
      emit('update', {
        id: obj.id,
      });
    }
  }
</script>

<template>
  <n-select
    v-model:value="appId"
    :consistent-menu-width="false"
    :label-field="'name'"
    :options="options"
    :value-field="'id'"
    clearable
    placeholder="请选择关联应用"
    @update:value="onUpdate"
  />
</template>

<style lang="less" scoped></style>
