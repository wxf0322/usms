import {Component, OnInit} from '@angular/core';
import {SelectItem} from 'primeng/primeng';

@Component({
  moduleId: module.id,
  selector: 'eve-header',
  templateUrl: 'header.component.html',
  styleUrls: ['header.component.css']
})

export class EveHeaderComponent implements OnInit {

  items: SelectItem[];

  ngOnInit(): void {
    this.items = [];
    this.items.push({label: '修改信息', value: '0'});
    this.items.push({label: '退出登入', value: '1'});
  }

  show() {
    let menu = document.getElementById('dropMenu');
    if (menu.style.display === 'none') {
      menu.style.display = 'block';
      menu.style.zIndex = '10000';
    } else if (menu.style.display === 'block') {
      menu.style.display = 'none';
    }
  }
}

