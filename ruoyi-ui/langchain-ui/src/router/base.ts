

import { ErrorPage, Layout, RedirectName } from '@/router/constant';
import { RouteRecordRaw } from 'vue-router';

// 404 on a page
export const ErrorPageRoute: RouteRecordRaw = {
  path: '/:path(.*)*',
  name: 'ErrorPage',
  component: Layout,
  meta: {
    title: 'ErrorPage',
    hideBreadcrumb: true,
  },
  children: [
    {
      path: '/:path(.*)*',
      name: 'ErrorPageSon',
      component: ErrorPage,
      meta: {
        title: 'ErrorPage',
        hideBreadcrumb: true,
      },
    },
  ],
};

export const RedirectRoute: RouteRecordRaw = {
  path: '/redirect',
  name: RedirectName,
  component: Layout,
  meta: {
    title: RedirectName,
    hideBreadcrumb: true,
  },
  children: [
    {
      path: '/redirect/:path(.*)',
      name: RedirectName + '_index',
      component: () => import('@/views/base/redirect/index.vue'),
      meta: {
        title: RedirectName,
        hideBreadcrumb: true,
      },
    },
  ],
};

export const BaseRoute: Array<any> = [
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: 'Layout',
    meta: {
      icon: 'FileTrayOutline',
      title: 'Dashboard',
    },
    children: [
      {
        path: 'console',
        name: 'dashboard_console',
        component: '/dashboard/index',
        meta: {
          title: '主控台',
        },
      },
    ],
  },
  {
    path: '/profile',
    name: 'profile',
    component: 'Layout',
    show: false,
    meta: {
      title: '个人中心',
    },
    children: [
      {
        path: 'index',
        name: 'profile_index',
        component: '/base/profile/index',
        show: false,
        meta: {
          title: '个人中心',
        },
      },
    ],
  },

  {
    path: '/app',
    name: 'app',
    component: 'Layout',
    show: false,
    meta: {
      title: 'AI应用管理',
    },
    children: [
      {
        path: 'info/:id?',
        name: 'appInfo',
        component: '/app/info',
        show: false,
        meta: {
          title: '应用配置',
        },
      },
    ],
  },
  {
    path: '/agi',
    name: 'aigc',
    component: 'Layout',
    show: false,
    meta: {
      title: '应用集成',
    },
    children: [
      {
        path: 'knowledge/:id?',
        name: 'knowledge_info',
        component: '/agi/knowledge/components/index',
        show: false,
        meta: {
          title: '知识库数据',
        },
      },
      {
        path: 'knowledge/info',
        name: 'knowledge_info_index',
        component: '/agi/knowledge/components/index',
        show: false,
        meta: {
          title: '知识库数据',
        },
      },
    ],
  },
];
