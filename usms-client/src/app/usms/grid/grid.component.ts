import {Component, OnInit} from '@angular/core';
import {SelectItem, TreeNode} from 'primeng/primeng';

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent implements OnInit {

  filesTree: TreeNode[];

  gridTree: TreeNode[];

  ngOnInit(): void {
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
        }, {
          'label': 'Resume.doc',
          'icon': 'fa-file-word-o',
          'data': 'Resume Document'
        }]
      }]
    }];

    this.gridTree = [
        {
          'data': {
            'name': 'Documents',
            'size': '75kb',
            'type': 'Folder'
          },
          'children': [
            {
              'data': {
                'name': 'Work',
                'size': '55kb',
                'type': 'Folder'
              },
              'children': [
                {
                  'data': {
                    'name': 'Expenses.doc',
                    'size': '30kb',
                    'type': 'Document'
                  }
                },
                {
                  'data': {
                    'name': 'Resume.doc',
                    'size': '25kb',
                    'type': 'Resume'
                  }
                }
              ]
            },
            {
              'data': {
                'name': 'Home',
                'size': '20kb',
                'type': 'Folder'
              },
              'children': [
                {
                  'data': {
                    'name': 'Invoices',
                    'size': '20kb',
                    'type': 'Text'
                  }
                }
              ]
            }
          ]
        },
        {
          'data': {
            'name': 'Pictures',
            'size': '150kb',
            'type': 'Folder'
          },
          'children': [
            {
              'data': {
                'name': 'barcelona.jpg',
                'size': '90kb',
                'type': 'Picture'
              }
            },
            {
              'data': {
                'name': 'primeui.png',
                'size': '30kb',
                'type': 'Picture'
              }
            },
            {
              'data': {
                'name': 'optimus.jpg',
                'size': '30kb',
                'type': 'Picture'
              }
            }
          ]
        }
      ];

  }


}
