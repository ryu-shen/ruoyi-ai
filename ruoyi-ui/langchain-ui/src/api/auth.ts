import { http } from '@/utils/http/axios';

/**
 * @description: 获取用户信息
 */
export function getUserInfo() {
  return http.request({
    url: '/upms/auth/info',
    method: 'get',
  });
}

/**
 * Token获取
 * @description: 用户登录
 */
export function login(data: any) {
  return http.request({
    url: `/upms/auth/login`,
    method: 'POST',
    data,
  });
}

export function register(data: any) {
  return http.request({
    url: `/upms/auth/register`,
    method: 'POST',
    data,
  });
}

/**
 * @description: 用户登出
 */
export function logout() {
  return http.request({
    url: '/upms/auth/logout',
    method: 'delete',
  });
}

/**
 * @description: 动态加载菜单
 * 需要在/settings/projectSettings.ts中配置permissionMode=BACK
 * 本项目不考虑从后段获取菜单
 */
export function getMenus() {
  return http.request({
    url: '/upms/menu/build',
    method: 'get',
  });
}

/**
 * 分页获取Token
 */
export function getTokens(params: any) {
  return http.request({
    url: '/upms/auth/token/page',
    method: 'get',
    params,
  });
}

/**
 * 删除Token
 */
export function delToken(id?: string) {
  return http.request({
    url: `/upms/auth/token/${id}`,
    method: 'delete',
  });
}
