import { BasicColumn } from '@/components/Table';
import { FormSchema } from '@/components/Form';
import { h } from 'vue';
import { NTag } from 'naive-ui';

export const columns: BasicColumn[] = [
  {
    title: '配置Key',
    key: 'configKey',
    align: 'center',
  },
  {
    title: '访问站点',
    key: 'endpoint',
    align: 'center',
  },
  {
    title: '自定义域名',
    key: 'domain',
    align: 'center',
  },
  {
    title: '桶名称',
    key: 'bucketName',
    align: 'center',
  },
  {
    title: '前缀',
    key: 'prefix',
    align: 'center',
  },
  {
    title: '域',
    key: 'region',
    align: 'center',
  },
  {
    title: '是否启用',
    key: 'status',
    align: 'center',
    width: 120,
    render(row) {
      return h(
        NTag,
        {
          type: row.status=='0' ? '是' : '否',
          size: 'small',
        },
        {
          default: () => (row.status=='0' ? '是' : '否'),
        }
      );
    },
  },

];

export const searchSchemas: FormSchema[] = [
  {
    field: 'name',
    component: 'NInput',
    label: '桶名称',
    componentProps: {
      placeholder: '请输入桶名称',
    },
  },
];

export const formSchemas: FormSchema[] = [
  {
    field: 'ossConfigId',
    label: 'ID',
    component: 'NInput',
    isHidden: true,
  },
  {
    field: 'configKey',
    label: '配置key',
    component: 'NInput',
    componentProps: {
      placeholder: '请输入配置key',
    },
    rules: [{ required: true, message: '请输入配置key', trigger: ['blur'] }],
  },
  {
    field: 'endpoint',
    label: '访问站点',
    component: 'NInput',
    componentProps: {
      placeholder: '请输入访问站点',
    },
    rules: [{ required: true, message: '请输入访问站点', trigger: ['blur'] }],
  },
  {
    field: 'domain',
    label: '自定义域名',
    component: 'NInput',
    componentProps: {
      placeholder: '请输入自定义域名',
    },
    rules: [{ required: false, message: '请输入自定义域名', trigger: ['blur'] }],
  },
  {
    field: 'accessKey',
    label: 'accessKey',
    component: 'NInput',
    componentProps: {
      placeholder: '请输入accessKey',
    },
    rules: [{ required: true, message: '请输入accessKey', trigger: ['blur'] }],
  },
  {
    field: 'secretKey',
    label: 'secretKey',
    component: 'NInput',
    componentProps: {
      placeholder: '请输入secretKey',
    },
    rules: [{ required: true, message: '请输入secretKey', trigger: ['blur'] }],
  },
  {
    field: 'bucketName',
    label: '桶名称',
    component: 'NInput',
    componentProps: {
      placeholder: '请输入桶名称',
    },
    rules: [{ required: true, message: '请输入桶名称', trigger: ['blur'] }],
  },
  {
    field: 'prefix',
    label: '前缀',
    component: 'NInput',
    componentProps: {
      placeholder: '请输入前缀',
    },
    rules: [{ required: false, message: '请输入前缀', trigger: ['blur'] }],
  },
  {
    field: 'status',
    label: '启用',
    component: 'NRadioGroup',
    componentProps: {
      options: [
        {
          label: '启用',
          value: '0',
        },
        {
          label: '禁用',
          value: '1',
        },
      ],
    },
    rules: [{ required: true }],
  },
  {
    field: 'isHttps',
    label: '是否HTTPS',
    component: 'NRadioGroup',
    componentProps: {
      options: [
        {
          label: '启用',
          value: 'Y',
        },
        {
          label: '禁用',
          value: 'N',
        },
      ],
    },
    rules: [{ required: true }],
  },
  {
    field: 'region',
    label: '域',
    component: 'NInput',
    componentProps: {
      placeholder: '请输入域',
    },
    rules: [{ required: false, message: '请输入域', trigger: ['blur'] }],
  },
  {
    field: 'remark',
    label: '备注',
    component: 'NInput',
    componentProps: {
      placeholder: '请输入备注',
    },
    rules: [{ required: false, message: '请输入备注', trigger: ['blur'] }],
  },
  {
    field: 'accessPolicy',
    label: '桶权限类型',
    component: 'NRadioGroup',
    componentProps: {
      options: [
        {
          label: 'private',
          value: '0',
        },
        {
          label: 'public',
          value: '1',
        },
        {
          label: 'custom',
          value: '2',
        },
      ],
    },
    rules: [{ required: true }],
  },

];
