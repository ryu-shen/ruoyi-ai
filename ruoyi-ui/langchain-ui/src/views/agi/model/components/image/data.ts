

export enum ProviderEnum {
  OPENAI = 'OPENAI',
  AZURE_OPENAI = 'AZURE_OPENAI',
  ZHIPU = 'ZHIPU',
}

export const LLMProviders: any[] = [
  {
    model: ProviderEnum.OPENAI,
    name: 'OpenAI',
    models: ['dall-e-2', 'dall-e-3'],
  },
  {
    model: ProviderEnum.AZURE_OPENAI,
    name: 'Azure OpenAI',
    models: ['dall-e-2', 'dall-e-3'],
  },
  {
    model: ProviderEnum.ZHIPU,
    name: '智谱清言',
    models: ['cogview-3'],
  },
];
