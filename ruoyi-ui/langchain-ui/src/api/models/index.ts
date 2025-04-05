export enum ModelTypeEnum {
  CHAT = 'CHAT',
  EMBEDDING = 'EMBEDDING',
  TEXT_IMAGE = 'TEXT_IMAGE',
  WEB_SEARCH = 'WEB_SEARCH',
}

export function hideKey(apiKey: string) {
  const key = apiKey;
  return key.slice(0, 13) + key.slice(13, -4).replace(/./g, '*') + key.slice(-4);
}
