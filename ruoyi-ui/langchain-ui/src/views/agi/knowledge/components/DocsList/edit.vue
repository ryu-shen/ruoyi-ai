

<script lang="ts" setup>
  import { nextTick, ref } from 'vue';
  import { add, getById, update } from '@/api/agi/docs';
  import { useMessage } from 'naive-ui';
  import { formSchemas } from './columns';
  import { BasicForm, useForm } from '@/components/Form';
  import { isNullOrWhitespace } from '@/utils/is';

  const emit = defineEmits(['reload']);
  const showModal = ref(false);
  const message = useMessage();

  const [register, { setFieldsValue }] = useForm({
    gridProps: { cols: 1 },
    labelWidth: 120,
    layout: 'horizontal',
    submitButtonText: '提交',
    schemas: formSchemas,
  });

  async function show(kbId: string, id: string) {
    showModal.value = true;
    await nextTick();
    if (id) {
      setFieldsValue(await getById(id));
    }
    setFieldsValue({ kbId });
  }

  async function handleSubmit(values: any) {
    if (values !== false) {
      showModal.value = false;
      if (isNullOrWhitespace(values.id)) {
        await add(values);
        emit('reload');
        message.success('新增成功');
      } else {
        await update(values);
        emit('reload');
        message.success('修改成功');
      }
    } else {
      message.error('请完善表单');
    }
  }

  defineExpose({ show });
</script>

<template>
  <n-modal
    v-model:show="showModal"
    :show-icon="false"
    preset="dialog"
    style="width: 40%"
    title="编辑文档"
  >
    <BasicForm class="mt-5" @register="register" @submit="handleSubmit" />
  </n-modal>
</template>

<style lang="less" scoped></style>
