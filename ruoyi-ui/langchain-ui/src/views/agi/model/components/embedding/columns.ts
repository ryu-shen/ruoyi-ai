

import { h } from 'vue';
import { NTag } from 'naive-ui';

export const baseColumns = [
  {
    title: '模型别名',
    key: 'name',
  },
  {
    title: '模型版本',
    key: 'model',
    width: '160',
  },
  {
    title: '向量纬度',
    key: 'dimension',
    align: 'center',
    width: '100',
    render(row) {
      return h(
        NTag,
        {
          size: 'small',
          type: 'error',
        },
        {
          default: () => row.dimension,
        }
      );
    },
  },
  {
    title: 'Api Key',
    key: 'apiKey',
  },
  {
    title: 'Base Url',
    key: 'baseUrl',
  },
];

export function getColumns() {
  return baseColumns;
}
