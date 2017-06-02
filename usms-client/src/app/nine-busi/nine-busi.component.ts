import {Component, OnInit} from '@angular/core';
import {EveMenuItem} from "../shared/component/side-menu/side-menu";
import {nineBusiMenus} from "../db.data";
/*
 * 九大业务根组件
 * */
@Component({
  moduleId: module.id,
  template: `
    <eve-side-menu [menuItems]="menuItems" [multiShow]="true"></eve-side-menu> 
    <div class="ui-g main-content">
      <div class="ui-g-12 ui-g-nopad"> 
        <router-outlet></router-outlet>
      </div> 
    </div>
`
})
export class NineBusiComponent implements OnInit {
  menuItems: EveMenuItem[];

  ngOnInit() {
    // 获取菜单数据
    this.menuItems = nineBusiMenus;
  }
}
