import {Component, OnInit, ViewChild} from '@angular/core';
import {UserPanelComponent} from "./user-panel/user-panel.component";
import {GridPanelComponent} from "./grid-panel/grid-panel.component";
import {SelectItem} from "primeng/primeng";

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent implements OnInit {

  types: SelectItem[];

  selectedType: string;

  ngOnInit(): void {
    this.types = [];
    this.types.push({label: '用户角度', value: 'user'});
    this.types.push({label: '网格角度', value: 'grid'});
    this.selectedType = 'user';
  }

}

