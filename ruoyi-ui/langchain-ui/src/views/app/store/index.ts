

import { defineStore } from 'pinia';
import { update } from '@/api/agi/app';

export interface AppState {
  activeMenu: string;
  info: any;
  modelId: string | null;
  knowledgeIds: any[];
  knowledges: any[];
}

export const useAppStore = defineStore('app-store', {
  state: (): AppState =>
    <AppState>{
      activeMenu: 'setting',
      info: {},
      modelId: '',
      knowledgeIds: [],
      knowledges: [],
    },

  getters: {},

  actions: {
    setActiveMenu(active: string) {
      this.activeMenu = active;
    },
    addKnowledge(item: any) {
      this.knowledgeIds.push(item.id);
      this.knowledges.push(item);
      this.updateInfo();
    },

    removeKnowledge(item: any) {
      this.knowledgeIds = this.knowledgeIds.filter((i) => i !== item.id);
      this.knowledges = this.knowledges.filter((i) => i.id !== item.id);
      this.updateInfo();
    },

    async updateInfo() {
      this.info.modelId = this.modelId;
      this.info.knowledgeIds = this.knowledgeIds;
      this.info.knowledges = this.knowledges;
      await update({ ...this.info });
    },
  },
});
