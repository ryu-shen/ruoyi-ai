import { ProviderEnum } from '@/views/agi/model/provider';

export const LLMProviders: any[] = [
  {
    model: ProviderEnum.OPENAI,
    name: 'OpenAI',
    models: ['text-embedding-3-small', 'text-embedding-3-large', 'text-embedding-ada-002'],
  },
  {
    model: ProviderEnum.Q_FAN,
    name: '百度千帆',
    models: ['bge-large-zh', 'bge-large-en', 'tao-8k'],
  },
  {
    model: ProviderEnum.Q_WEN,
    name: '阿里百炼',
    models: ['text-embedding-v3'],
  },
  {
    model: ProviderEnum.ZHIPU,
    name: '智谱清言',
    models: ['embedding-2', 'embedding-3'],
  },
  {
    model: ProviderEnum.DOUYIN,
    name: '抖音豆包',
    models: ['text-240715', 'text-240515'],
  },
  {
    model: ProviderEnum.OLLAMA,
    name: 'Ollama',
    models: ['text2vec-bge-large-chinese:latest'],
  },
];
