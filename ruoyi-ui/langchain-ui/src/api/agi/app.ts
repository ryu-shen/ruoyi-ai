import {http} from '../../utils/http/axios';

export function list(params: any) {
  return http.request({
    url: '/agi/app/list',
    method: 'get',
    params,
  });
}

export function getAppApiChannel(appId: any) {
  return http.request({
    url: `/agi/app/channel/api/${appId}`,
    method: 'get',
  });
}

export function page(params: any) {
  return http.request({
    url: '/agi/app/page',
    method: 'get',
    params,
  });
}

export function getById(id: string) {
  return http.request({
    url: `/agi/app/${id}`,
    method: 'get',
  });
}

export function getByModelId(id: string) {
  return http.request({
    url: `/agi/app/byModelId/${id}`,
    method: 'get',
  });
}

export function add(params: any) {
  return http.request({
    url: '/agi/app',
    method: 'post',
    params,
  });
}

export function update(params: any) {
  return http.request({
    url: '/agi/app',
    method: 'put',
    params,
  });
}

export function del(id: string) {
  return http.request({
    url: `/agi/app/${id}`,
    method: 'delete',
  });
}
