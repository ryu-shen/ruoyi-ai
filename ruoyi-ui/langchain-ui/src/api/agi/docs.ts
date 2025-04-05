import { http } from '@/utils/http/axios';

export function list(params: any) {
  return http.request({
    url: '/agi/docs/list',
    method: 'get',
    params,
  });
}

export function page(params: any) {
  return http.request({
    url: '/agi/docs/page',
    method: 'get',
    params,
  });
}

export function getById(id: string) {
  return http.request({
    url: `/agi/docs/${id}`,
    method: 'get',
  });
}

export function add(params: any) {
  return http.request({
    url: '/agi/docs',
    method: 'post',
    params,
  });
}

export function update(params: any) {
  return http.request({
    url: '/agi/docs',
    method: 'put',
    params,
  });
}

export function reEmbed(id: string) {
  return http.request({
    url: `/agi/embedding/re-embed/${id}`,
    method: 'get',
  });
}

export function del(id: string) {
  return http.request({
    url: `/agi/docs/${id}`,
    method: 'delete',
  });
}
