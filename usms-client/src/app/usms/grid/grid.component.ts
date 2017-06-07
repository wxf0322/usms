import {Component, OnInit} from '@angular/core';
import {SelectItem, TreeNode} from 'primeng/primeng';

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent implements OnInit {

  cities: SelectItem[];

  selectedCity: string;

  filesTree: TreeNode[];

  ngOnInit(): void {
    this.cities = [];
    this.cities.push({label: 'New York', value: {id: 1, name: 'New York', code: 'NY'}});
    this.cities.push({label: 'Rome', value: {id: 2, name: 'Rome', code: 'RM'}});

    this.filesTree = [{
      'label': 'Documents',
      'data': 'Documents Folder',
      'expandedIcon': 'fa-folder-open',
      'collapsedIcon': 'fa-folder',
      'children': [{
        'label': 'Work',
        'data': 'Work Folder',
        'expandedIcon': 'fa-folder-open',
        'collapsedIcon': 'fa-folder',
        'children': [{
          'label': 'Expenses.doc',
          'icon': 'fa-file-word-o',
          'data': 'Expenses Document'
        }, {'label': 'Resume.doc', 'icon': 'fa-file-word-o', 'data': 'Resume Document'}]
      }]
    }];
  }


}
