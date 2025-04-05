import { http } from '@/utils/http/axios';

export function list(params: any) {
  return http.request({
    url: '/agi/app/api/list',
    method: 'get',
    params,
  });
}

export function page(params: any) {
  return http.request({
    url: '/agi/app/api/page',
    method: 'get',
    params,
  });
}

export function getById(id: any) {
  return http.request({
    url: `/agi/app/api/${id}`,
    method: 'get',
  });
}

export function createApi(id: any, channel: any) {
  return http.request({
    url: `/agi/app/api/create/${id}/${channel}`,
    method: 'get',
  });
}

export function add(params: any) {
  return http.request({
    url: '/agi/app/api',
    method: 'post',
    params,
  });
}

export function update(params: any) {
  return http.request({
    url: '/agi/app/api',
    method: 'put',
    params,
  });
}

export function del(id?: string) {
  return http.request({
    url: `/agi/app/api/${id}`,
    method: 'delete',
  });
}
