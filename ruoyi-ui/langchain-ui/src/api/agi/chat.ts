import { http } from '@/utils/http/axios';
import { AxiosProgressEvent } from 'axios';

export function chat(
  data: any,
  controller: AbortController,
  onDownloadProgress?: (progressEvent: AxiosProgressEvent) => void
) {
  return http.request(
    {
      method: 'post',
      url: '/agi/chat/completions',
      data,
      signal: controller.signal,
      onDownloadProgress: onDownloadProgress,
    },
    {
      isReturnNativeResponse: true,
    }
  );
}

export function clean(conversationId: string | null) {
  return http.request({
    url: `/agi/chat/messages/clean/${conversationId}`,
    method: 'delete',
  });
}

export function getMessages(conversationId?: string) {
  return http.request({
    url: `/agi/chat/messages/${conversationId}`,
    method: 'get',
  });
}

export function getAppInfo(params: any) {
  return http.request({
    url: `/agi/app/info`,
    method: 'get',
    params,
  });
}

export function getImageModels() {
  return http.request({
    method: 'get',
    url: '/agi/chat/getImageModels',
  });
}

/**
 * @description 生成图片
 */
export function genImage(data: any) {
  return http.request({
    url: '/agi/chat/image',
    method: 'post',
    data: data,
  });
}

/**
 * @description: 生成思维导图
 */
export function genMindMap(data: any) {
  return http.request({
    url: '/agi/chat/mindmap',
    method: 'post',
    data: data,
  });
}
