

<script lang="ts" setup>
  import { h, reactive, ref } from 'vue';
  import SvgIcon from '@/components/SvgIcon/index.vue';
  import { createApi, del, list as getApiList } from '@/api/agi/appApi';
  import { useDialog, useMessage } from 'naive-ui';
  import { useRouter } from 'vue-router';
  import { copyToClip } from '@/utils/copy';
  import { BasicTable, TableAction } from '@/components/Table';
  import { CopyOutlined, DeleteOutlined } from '@vicons/antd';
  import { hideKey } from '@/api/models';

  const emit = defineEmits(['reload']);
  const props = defineProps({
    channel: {
      type: String,
      required: true,
    },
  });

  const ms = useMessage();
  const dialog = useDialog();
  const router = useRouter();
  const actionRef = ref();

  async function onSubmit() {
    await createApi(router.currentRoute.value.params.id, props.channel);
    ms.success('新增成功');
    reloadTable();
  }

  async function onCopy(record: any) {
    await copyToClip(record.apiKey);
    ms.success('秘钥复制成功');
  }

  const actionColumn = reactive({
    width: 120,
    title: '操作',
    key: 'action',
    fixed: 'right',
    align: 'center',
    render(record: any) {
      return h(TableAction as any, {
        style: 'text',
        actions: [
          {
            type: 'success',
            label: '复制',
            size: 'tiny',
            icon: CopyOutlined,
            onClick: onCopy.bind(null, record),
          },
          {
            type: 'error',
            label: '删除',
            size: 'tiny',
            icon: DeleteOutlined,
            onClick: handleDelete.bind(null, record),
          },
        ],
      });
    },
  });

  function handleDelete(item) {
    dialog.warning({
      title: '提示！',
      content: '你确定重置Key吗？删除后原Key将立即失效是，请谨慎操作',
      positiveText: '是',
      negativeText: '否',
      onPositiveClick: async () => {
        await del(item.id);
        ms.success('删除成功');
      },
    });
  }

  const loadDataTable = async (res: any) => {
    return await getApiList({ appId: router.currentRoute.value.params.id, channel: props.channel });
  };

  const columns = [
    {
      title: '秘钥',
      key: 'apiKey',
      align: 'center',
      render: (rows: any) => {
        return hideKey(rows.apiKey);
      },
    },
    {
      title: '创建时间',
      key: 'createTime',
      align: 'center',
      width: 180,
    },
  ];

  function reloadTable() {
    actionRef.value.reload();
  }
</script>

<template>
  <BasicTable
    ref="actionRef"
    :actionColumn="actionColumn"
    :columns="columns"
    :pagination="false"
    :request="loadDataTable"
    :row-key="(row:any) => row.id"
    :single-line="false"
    :size="'small'"
  >
    <template #tableTitle>
      <n-button size="small" type="primary" @click="onSubmit">
        <template #icon>
          <SvgIcon icon="ic:round-plus" />
        </template>
        创建秘钥
      </n-button>
    </template>
  </BasicTable>
</template>

<style lang="less" scoped></style>
