import { http } from '@/utils/http/axios';

export function page(params: any) {
  return http.request({
    url: '/agi/conversation/page',
    method: 'get',
    params,
  });
}

export function del(id: string) {
  return http.request({
    url: `/agi/conversation/${id}`,
    method: 'delete',
  });
}

export function getMessages(conversationId: string) {
  return http.request({
    url: `/agi/conversation/messages/${conversationId}`,
    method: 'get',
  });
}
