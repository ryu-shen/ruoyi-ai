import { http } from '@/utils/http/axios';

export function list(params: any) {
  return http.request({
    url: '/agi/knowledge/list',
    method: 'get',
    params,
  });
}

export function page(params: any) {
  return http.request({
    url: '/agi/knowledge/page',
    method: 'get',
    params,
  });
}

export function getById(id: string) {
  return http.request({
    url: `/agi/knowledge/${id}`,
    method: 'get',
  });
}

export function add(params: any) {
  return http.request({
    url: '/agi/knowledge',
    method: 'post',
    params,
  });
}

export function update(params: any) {
  return http.request({
    url: '/agi/knowledge',
    method: 'put',
    params,
  });
}

export function del(id?: string) {
  return http.request({
    url: `/agi/knowledge/${id}`,
    method: 'delete',
  });
}
