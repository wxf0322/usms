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
    this.cities.push({label: 'London', value: {id: 3, name: 'London', code: 'LDN'}});
    this.cities.push({label: 'Istanbul', value: {id: 4, name: 'Istanbul', code: 'IST'}});
    this.cities.push({label: 'Paris', value: {id: 5, name: 'Paris', code: 'PRS'}});

    this.filesTree = [{
      "label": "Documents",
      "data": "Documents Folder",
      "expandedIcon": "fa-folder-open",
      "collapsedIcon": "fa-folder",
      "children": [{
        "label": "Work",
        "data": "Work Folder",
        "expandedIcon": "fa-folder-open",
        "collapsedIcon": "fa-folder",
        "children": [{
          "label": "Expenses.doc",
          "icon": "fa-file-word-o",
          "data": "Expenses Document"
        }, {"label": "Resume.doc", "icon": "fa-file-word-o", "data": "Resume Document"}]
      },
        {
          "label": "Home",
          "data": "Home Folder",
          "expandedIcon": "fa-folder-open",
          "collapsedIcon": "fa-folder",
          "children": [{"label": "Invoices.txt", "icon": "fa-file-word-o", "data": "Invoices for this month"}]
        }]
    }];
  }


}
