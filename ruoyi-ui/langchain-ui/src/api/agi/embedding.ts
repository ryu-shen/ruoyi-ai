import { http } from '@/utils/http/axios';
import { AxiosProgressEvent } from 'axios';

export function embeddingText(params: any) {
  return http.request({
    url: '/agi/embedding/text',
    method: 'post',
    params,
  });
}

export function embeddingSearch(data: any) {
  return http.request({
    url: '/agi/embedding/search',
    method: 'post',
    data,
  });
}

export function embeddingDocs(
  knowledgeId: string,
  data: any,
  onUploadProgress?: (progressEvent: AxiosProgressEvent) => void
) {
  return http.request({
    url: `/agi/embedding/docs/${knowledgeId}`,
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    onUploadProgress,
  });
}
