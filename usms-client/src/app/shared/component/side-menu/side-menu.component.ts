import {Component, OnInit, Input} from "@angular/core"
import {EveMenuItem} from "./side-menu";

@Component({
  moduleId: module.id,
  selector: 'eve-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.css']
})

/**
 * 描述：侧边菜单组件
 *
 * @author Warren Chen
 * @created 2017 /01/17 17:25:22
 */
export class SideMenuComponent implements OnInit {
  @Input() menuItems: EveMenuItem[];
  @Input() multiShow: boolean; //是否允许同时展开多个
  private showMe: any = {};

  ngOnInit(): void {
  }

  /*导航*/
  navigateTo(routLink: string) {
  }

  /*展开、收缩菜单*/
  toggleSubMenu(menuItem: EveMenuItem) {
    let curr = this.showMe[menuItem.title]; // 存储当前面板状态
    Object.keys(this.showMe).forEach(title => {
      this.showMe[title] = false;
    });
    this.showMe[menuItem.title] = !curr;
  }

}

