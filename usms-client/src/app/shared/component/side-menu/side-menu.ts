import {EventEmitter} from "@angular/core";

export interface EveMenuItem {
  title?: string; // 菜单名称
  icon?: string; // 图标
  command?: (event?: any) => void; //
  url?: string; // 超链接
  target?: string; // 超链接打开方式
  routerLink?: any; // 路由链接
  eventEmitter?: EventEmitter<any>; // 事件发射
  children?: EveMenuItem[]; // 子菜单
  expanded?: boolean; //  是否展开
  selected?: boolean; //  是否被选中
  disabled?: boolean; // 是否被禁用
}
