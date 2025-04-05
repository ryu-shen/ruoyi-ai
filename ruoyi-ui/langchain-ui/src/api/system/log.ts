import { http } from '@/utils/http/axios';

export function page(params: any) {
  return http.request({
    url: '/system/log/page',
    method: 'get',
    params,
  });
}

export function getById(id: string) {
  return http.request({
    url: `/system/log/${id}`,
    method: 'get',
  });
}

export function del(id?: string) {
  return http.request({
    url: `/system/log/${id}`,
    method: 'delete',
  });
}
