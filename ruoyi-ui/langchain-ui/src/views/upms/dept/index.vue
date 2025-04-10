<script lang="ts" setup>
  import { onMounted, ref } from 'vue';
  import { AlertCircleOutline } from '@vicons/ionicons5';
  import { del, tree as getPage } from '@/api/upms/dept';
  import 'vue3-tree-org/lib/vue3-tree-org.css';
  import { Vue3TreeOrg } from 'vue3-tree-org';
  import Edit from './edit.vue';
  import { useDialog, useMessage } from 'naive-ui';

  const message = useMessage();
  const dialog = useDialog();

  const editRef = ref();
  const data = ref({});
  const collapsable = ref();
  const loading = ref(true);
  const menus = [
    { name: '添加部门', command: 'add' },
    { name: '编辑部门', command: 'edit' },
    { name: '删除部门', command: 'delete' },
  ];

  onMounted(async () => {
    await reloadTable();
  });

  async function reloadTable() {
    loading.value = true;
    const list = await getPage({});
    if (list != null && list.length > 0) {
      data.value = list[0];
    }
    loading.value = false;
  }

  function handleAdd(node: any) {
    editRef.value.show(null, node.id);
  }

  function handleEdit(record: Recordable) {
    console.log(record);
    editRef.value.show(record.id);
  }

  function handleClick(e, data) {
    handleEdit(data);
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
        await reloadTable();
      },
      onNegativeClick: () => {},
    });
  }

  function onExpand() {
    collapsable.value = !collapsable.value;
  }
</script>

<template>
  <div class="h-full">
    <div class="n-layout-page-header mb-4">
      <n-card :bordered="false">
        <template #header>
          <div class="flex flex-wrap items-start">
            <n-popover class="custom-popover">
              <template #trigger>
                <span class="tips-line">部门管理</span>
              </template>
              右键或双击节点进行自定义操作
            </n-popover>

            <n-icon class="ml-1" size="14">
              <AlertCircleOutline />
            </n-icon>
          </div>
        </template>

        <template #footer>
          <n-space>
            <n-button v-if="data == null" secondary size="small" type="info">新增顶层部门</n-button>
            <n-button secondary size="small" type="info" @click="handleAdd">新增</n-button>
            <n-button secondary size="small" type="success" @click="onExpand">展开/折叠</n-button>
          </n-space>
        </template>
      </n-card>
    </div>

    <n-spin :show="loading">
      <vue3-tree-org
        :collapsable="collapsable"
        :data="data"
        :define-menus="menus"
        :expandAll="true"
        :node-add="handleAdd"
        :node-delete="handleDelete"
        :node-edit="handleEdit"
        :props="{ id: 'id', pid: 'parentId', label: 'name', children: 'children' }"
        center
        @on-node-dblclick="handleClick"
      />
    </n-spin>

    <Edit ref="editRef" @reload="reloadTable" />
  </div>
</template>

<style lang="less" scoped>
  ::v-deep(.zm-draggable) {
    margin-top: 4px;
  }
  .zm-tree-org {
    height: calc(100vh - 265px);
    color: #1f2225;
  }
</style>
