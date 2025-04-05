<script lang="ts" setup>
  import { nextTick, ref } from 'vue';
  import { add, update, getById } from '@/api/system/ossconfig';
  import { useMessage } from 'naive-ui';
  import { formSchemas } from './columns';
  import { basicModal, useModal } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
  import { isNullOrWhitespace } from '@/utils/is';

  const emit = defineEmits(['reload']);
  const message = useMessage();

  const [
    modalRegister,
    { openModal: openModal, closeModal: closeModal, setSubLoading: setSubLoading },
  ] = useModal({
    title: '新增/编辑',
    closable: true,
    maskClosable: false,
    showCloseBtn: false,
    showSubBtn: false,
  });

  const [register, { setFieldsValue, clearValidate, setProps }] = useForm({
    gridProps: { cols: 2 },
    labelWidth: 120,
    layout: 'horizontal',
    submitButtonText: '提交',
    schemas: formSchemas,
  });

  async function show(ossConfigId: string) {
    openModal();
    await nextTick();
    if (ossConfigId) {
      setFieldsValue(await getById(ossConfigId));
    } else {
      setFieldsValue({ status: true, sex: '男' });
    }
    // 隐藏密码输入框
    const filterSchemas = formSchemas.filter((i) => {
      if (i.field == 'password') {
        i.isHidden = !isNullOrWhitespace(ossConfigId);
      }
      clearValidate();
      return true;
    });
    await setProps({ schemas: filterSchemas });
  }

  async function handleSubmit(values: any) {
    if (values !== false) {
      if (isNullOrWhitespace(values.ossConfigId)) {
        await add(values);
        closeModal();
        emit('reload');
        message.success('新增成功');
      } else {
        await update(values);
        closeModal();
        emit('reload');
        message.success('修改成功');
      }
    } else {
      setSubLoading(false);
      message.error('请完善表单');
    }
  }
  defineExpose({ show });
</script>

<template>
  <basicModal @register="modalRegister" style="width: 45%">
    <template #default>
      <BasicForm @register="register" @submit="handleSubmit" class="mt-5">

      </BasicForm>
    </template>
  </basicModal>
</template>

<style scoped lang="less"></style>
