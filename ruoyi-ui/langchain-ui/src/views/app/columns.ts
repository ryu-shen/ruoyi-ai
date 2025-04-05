

import { FormSchema } from '@/components/Form';

export const CHANNEL = {
  API: 'CHANNEL_API',
  WEB: 'CHANNEL_WEB',
};

export const searchSchemas: FormSchema[] = [
  {
    field: 'title',
    component: 'NInput',
    label: '标题',
    componentProps: {
      placeholder: '请输入Prompt标题查询',
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
    label: '应用名称',
    component: 'NInput',
    rules: [{ required: true, message: '请输入应用名称', trigger: ['blur'] }],
  },
  {
    field: 'modelId',
    label: '关联模型',
    component: 'NInput',
    slot: 'modelIdSlot',
    rules: [{ required: true, message: '请选择关联模型', trigger: ['blur'] }],
  },
  {
    field: 'cover',
    label: '应用封面',
    slot: 'coverSlot',
    component: 'NInput',
  },
  {
    field: 'des',
    label: '应用描述',
    component: 'NInput',
    componentProps: {
      isFull: true,
      placeholder: '请输入应用描述',
      type: 'textarea',
      autosize: {
        minRows: 5,
        maxRows: 8,
      },
    },
    // rules: [{ required: true, message: '请输入应用描述', trigger: ['blur'] }],
  },
];
