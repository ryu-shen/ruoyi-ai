

import { FormSchema } from '@/components/Form';

export const searchSchemas: FormSchema[] = [
  {
    field: 'name',
    component: 'NInput',
    label: '知识库名称',
    componentProps: {
      placeholder: '请输入知识库名称',
    },
  },
];

export const formSchemas: FormSchema[] = [
  {
    field: 'id',
    label: 'ID',
    component: 'NInput',
    isHidden: true,
  },
  {
    field: 'name',
    component: 'NInput',
    label: '知识库名称',
    componentProps: {
      placeholder: '请输入知识库名称',
    },
    rules: [{ required: true, message: '请输入知识库名称', trigger: ['blur'] }],
  },
  {
    field: 'embedStoreId',
    label: '向量数据库',
    component: 'NInput',
    slot: 'embedStoreSlot',
    rules: [{ required: true, message: '请选择关联向量数据库', trigger: ['blur'] }],
  },
  {
    field: 'embedModelId',
    label: '向量模型',
    component: 'NInput',
    slot: 'embedModelSlot',
    rules: [{ required: true, message: '请选择关联向量模型', trigger: ['blur'] }],
  },
  {
    field: 'des',
    component: 'NInput',
    label: '知识库描述',
    componentProps: {
      placeholder: '请输入知识库描述',
      type: 'textarea',
      autosize: {
        minRows: 3,
        maxRows: 3,
      },
    },
    rules: [{ required: true, message: '请输入知识库描述', trigger: ['blur'] }],
  },
];
