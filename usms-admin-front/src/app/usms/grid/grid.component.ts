import {Component, OnInit,ViewChild} from '@angular/core';
import {UserPanelComponent} from "./user-panel/user-panel.component";
import {GridPanelComponent} from "./grid-panel/grid-panel.component";

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent implements OnInit {

  /**
   * 关联用户组件
   */
  @ViewChild(UserPanelComponent)
  userPanelComponent : UserPanelComponent;

  /**
   * 关联网格组件
   */
  @ViewChild(GridPanelComponent)
  gridPanelComponent : GridPanelComponent;
  ngOnInit(): void {


  }
  gridSaved(event) {
    if(event === 'refreshtable'){
      this.userPanelComponent.getDataByPage(0,10,null);
    }
    if(event === 'refreshtree'){
      this.gridPanelComponent.refreshTree();
    }
  }
}

