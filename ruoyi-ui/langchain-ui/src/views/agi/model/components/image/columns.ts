

import { ProviderEnum } from './data';

export const baseColumns = [
  {
    title: '模型别名',
    key: 'name',
  },
  {
    title: '模型版本',
    key: 'model',
  },
];

export const openaiColumns = [
  ...baseColumns,
  {
    title: 'Api Key',
    key: 'apiKey',
  },
];

export const azureOpenaiColumns = [
  ...baseColumns,
  {
    title: 'Api Key',
    key: 'apiKey',
  },
  {
    title: 'Endpoint',
    key: 'endpoint',
  },
  {
    title: 'Deployment Name',
    key: 'azureDeploymentName',
  },
];

export const zhipuColumns = [...baseColumns];

export function getColumns(provider: string) {
  switch (provider) {
    case ProviderEnum.OPENAI: {
      return openaiColumns;
    }
    case ProviderEnum.AZURE_OPENAI: {
      return azureOpenaiColumns;
    }
    case ProviderEnum.ZHIPU: {
      return zhipuColumns;
    }
  }
  return [];
}
