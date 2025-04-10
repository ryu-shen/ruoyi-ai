

<script lang="ts" setup>
  import { h, reactive, ref } from 'vue';
  import { BasicTable, TableAction } from '@/components/Table';
  import { BasicForm, useForm } from '@/components/Form/index';
  import { del, page as getPage } from '@/api/agi/message';
  import { columns, searchSchemas } from './columns';
  import { DeleteOutlined } from '@vicons/antd';
  import { useDialog, useMessage } from 'naive-ui';
  import ConversationList from './conversation/index.vue';

  const message = useMessage();
  const dialog = useDialog();
  const actionRef = ref();

  const actionColumn = reactive({
    width: 70,
    title: '操作',
    key: 'action',
    fixed: 'right',
    align: 'center',
    render(record: any) {
      return h(TableAction as any, {
        style: 'text',
        actions: [
          {
            type: 'error',
            icon: DeleteOutlined,
            onClick: handleDelete.bind(null, record),
          },
        ],
      });
    },
  });

  const [register, { getFieldsValue }] = useForm({
    gridProps: { cols: '1 s:1 m:2 l:3 xl:4 2xl:4' },
    labelWidth: 80,
    schemas: searchSchemas,
    showAdvancedButton: false,
  });

  const loadDataTable = async (res: any) => {
    return await getPage({ ...getFieldsValue(), ...res });
  };

  function reloadTable() {
    actionRef.value.reload();
  }

  function handleDelete(record: Recordable) {
    dialog.info({
      title: '提示',
      content: `您确定删除这条数据吗`,
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
  <n-card :bordered="false">
    <n-tabs animated type="line">
      <n-tab-pane name="1" tab="会话消息列表">
        <div class="mt-2">
          <BasicForm @register="register" @reset="handleReset" @submit="reloadTable" />

          <BasicTable
            ref="actionRef"
            :actionColumn="actionColumn"
            :columns="columns"
            :request="loadDataTable"
            :row-key="(row:any) => row.id"
            :single-line="false"
            :size="'small'"
          />
        </div>
      </n-tab-pane>
      <n-tab-pane name="2" tab="会话窗口列表">
        <ConversationList />
      </n-tab-pane>
    </n-tabs>
  </n-card>
</template>

<style lang="less" scoped></style>
