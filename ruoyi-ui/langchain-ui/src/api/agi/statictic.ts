import { http } from '@/utils/http/axios';

export function getReqChartBy30() {
  return http.request({
    url: `/agi/statistic/requestBy30`,
    method: 'get',
  });
}

export function getReqChart() {
  return http.request({
    url: `/agi/statistic/request`,
    method: 'get',
  });
}

export function getTokenChartBy30() {
  return http.request({
    url: `/agi/statistic/tokenBy30`,
    method: 'get',
  });
}

export function getTokenChart() {
  return http.request({
    url: `/agi/statistic/token`,
    method: 'get',
  });
}

export function getHomeData() {
  return http.request({
    url: `/agi/statistic/home`,
    method: 'get',
  });
}
