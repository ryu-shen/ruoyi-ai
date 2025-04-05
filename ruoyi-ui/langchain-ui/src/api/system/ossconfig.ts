import { http } from '@/utils/http/axios';

export function list(params: any) {
  return http.request({
    url: '/system/oss/config/list',
    method: 'get',
    params,
  });
}

export function page(params: any) {
  return http.request({
    url: '/system/oss/config/page',
    method: 'get',
    params,
  });
}

export function getById(ossConfigId: any) {
  return http.request({
    url: `/system/oss/config/${ossConfigId}`,
    method: 'get',
  });
}

export function add(data: any) {
  return http.request({
    url: '/system/oss/config',
    method: 'post',
    data,
  });
}

export function update(data: any) {
  return http.request({
    url: '/system/oss/config',
    method: 'put',
    data,
  });
}

export function del(ossConfigIds: any) {
  return http.request({
    url: `/system/oss/config/${ossConfigIds}`,
    method: 'delete',
  });
}
