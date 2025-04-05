import { http } from '@/utils/http/axios';

export function list(params: any) {
  return http.request({
    url: '/agi/docs/slice/list',
    method: 'get',
    params,
  });
}

export function page(params: any) {
  return http.request({
    url: '/agi/docs/slice/page',
    method: 'get',
    params,
  });
}

export function getById(id: string) {
  return http.request({
    url: `/agi/docs/slice/${id}`,
    method: 'get',
  });
}

export function add(params: any) {
  return http.request({
    url: '/agi/docs/slice',
    method: 'post',
    params,
  });
}

export function update(params: any) {
  return http.request({
    url: '/agi/docs/slice',
    method: 'put',
    params,
  });
}

export function del(id: string) {
  return http.request({
    url: `/agi/docs/slice/${id}`,
    method: 'delete',
  });
}
