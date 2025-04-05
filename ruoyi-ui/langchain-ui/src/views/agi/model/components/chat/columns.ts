

import { ProviderEnum } from '@/views/agi/model/provider';

export const baseColumns = [
  {
    title: '模型别名',
    key: 'name',
    align: 'center',
  },
  {
    title: '模型版本',
    key: 'model',
    align: 'center',
    width: '140',
  },
  {
    title: '回复上限',
    key: 'responseLimit',
    align: 'center',
    width: '100',
  },
  {
    title: '生成随机性',
    key: 'temperature',
    align: 'center',
    width: '100',
  },
  {
    title: 'Top P',
    key: 'topP',
    align: 'center',
    width: '100',
  },
];

export const openaiColumns = [
  ...baseColumns,
  {
    title: 'Api Key',
    key: 'apiKey',
    align: 'center',
  },
];

export const ollamaColumns = [
  ...baseColumns,
  {
    title: 'Base Url',
    key: 'baseUrl',
    align: 'center',
  },
];

export const qfanColumns = [
  ...baseColumns,
  {
    title: 'Api Key',
    key: 'apiKey',
    align: 'center',
  },
];

export const qwenColumns = [
  ...baseColumns,
  {
    title: 'Api Key',
    key: 'apiKey',
    align: 'center',
  },
];
export const zhipuColumns = [
  ...baseColumns,
  {
    title: 'Api Key',
    key: 'apiKey',
    align: 'center', 
  },
];

export function getColumns(provider: string) {
  switch (provider) {
    case ProviderEnum.OLLAMA: {
      return ollamaColumns;
    }
    case ProviderEnum.Q_FAN: {
      return qfanColumns;
    }
    case ProviderEnum.Q_WEN: {
      return qwenColumns;
    }
    case ProviderEnum.ZHIPU: {
      return zhipuColumns;
    }
  }
  return openaiColumns;
}
