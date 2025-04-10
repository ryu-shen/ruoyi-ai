

<script lang="ts" setup>
  import { h, nextTick, reactive, ref } from 'vue';
  import { BasicTable, TableAction } from '@/components/Table';
  import { BasicForm, useForm } from '@/components/Form/index';
  import { del, page as getPage } from '@/api/agi/embed-store';
  import { columns, ProviderConst, searchSchemas } from './columns';
  import { DeleteOutlined, EditOutlined, PlusOutlined } from '@vicons/antd';
  import Edit from './edit.vue';
  import { useDialog, useMessage } from 'naive-ui';

  const message = useMessage();
  const dialog = useDialog();
  const provider = ref('');
  const actionRef = ref();
  const editRef = ref();

  const actionColumn = reactive({
    width: 100,
    title: '操作',
    key: 'action',
    fixed: 'right',
    align: 'center',
    render(record: any) {
      return h(TableAction as any, {
        style: 'text',
        actions: [
          {
            type: 'info',
            icon: EditOutlined,
            onClick: handleEdit.bind(null, record),
          },
          {
            type: 'error',
            icon: DeleteOutlined,
            onClick: handleDelete.bind(null, record),
          },
        ],
      });
    },
  });

  const [register, { getFieldsValue, setFieldsValue }] = useForm({
    gridProps: { cols: '1 s:1 m:2 l:3 xl:4 2xl:4' },
    labelWidth: 100,
    schemas: searchSchemas,
    showAdvancedButton: false,
  });

  const loadDataTable = async (res: any) => {
    return await getPage({ ...getFieldsValue(), ...res });
  };

  function reloadTable() {
    actionRef.value.reload();
  }

  async function handleAdd(val: any) {
    provider.value = val;
    await nextTick();
    editRef.value.show();
  }

  async function handleEdit(record: Recordable) {
    provider.value = record.provider;
    await nextTick();
    editRef.value.show(record.id);
  }

  function handleDelete(record: Recordable) {
    dialog.info({
      title: '提示',
      content: `您想删除 ${record.name}`,
      positiveText: '确定',
      negativeText: '取消',
      onPositiveClick: async () => {
        await del(record.id);
        message.success('删除成功');
        reloadTable();
      },
      onNegativeClick: () => {},
    });
  }

  function handleReset(values: Recordable) {
    reloadTable();
  }
</script>

<template>
  <div class="h-full">
    <n-card :bordered="false">
      <BasicForm @register="register" @reset="handleReset" @submit="reloadTable" />
      <n-alert
        class="w-full mb-4 mt-2 min-alert"
        title="注意：请慎重修改模型的向量纬度参数（Dimension），此参数需要和向量库匹配（错误修改可能将影响已有的向量数据）"
        type="info"
      />

      <BasicTable
        ref="actionRef"
        :actionColumn="actionColumn"
        :columns="columns"
        :request="loadDataTable"
        :row-key="(row:any) => row.id"
        :single-line="false"
        :size="'small'"
      >
        <template #tableTitle>
          <n-popselect
            v-model:value="provider"
            :options="ProviderConst"
            trigger="click"
            @update:value="handleAdd"
          >
            <n-button type="primary">
              <template #icon>
                <n-icon>
                  <PlusOutlined />
                </n-icon>
              </template>
              新增向量数据库
            </n-button>
          </n-popselect>
        </template>
      </BasicTable>
    </n-card>

    <Edit ref="editRef" :provider="provider" @reload="reloadTable" />
  </div>
</template>

<style lang="less" scoped></style>
