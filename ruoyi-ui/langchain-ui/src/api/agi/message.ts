import { http } from '@/utils/http/axios';

export function list(params: any) {
  return http.request({
    url: '/agi/message/list',
    method: 'get',
    params,
  });
}

export function page(params: any) {
  return http.request({
    url: '/agi/message/page',
    method: 'get',
    params,
  });
}

export function add(params: any) {
  return http.request({
    url: '/agi/message',
    method: 'post',
    params,
  });
}

export function update(params: any) {
  return http.request({
    url: '/agi/message',
    method: 'put',
    params,
  });
}

export function del(id: string) {
  return http.request({
    url: `/agi/message/${id}`,
    method: 'delete',
  });
}
