import {Component, OnInit} from '@angular/core';
import {TreeNode, SelectItem} from 'primeng/primeng';
import {Router, ActivatedRoute} from '@angular/router';
import {HttpService} from '../../core/service/http.service';
import {ConfirmationService} from 'primeng/primeng';
import {TreeData} from 'app/shared/util/tree-data';
import {TreeUtil} from '../../shared/util/tree-util';
import {Operation} from './operation';
import {OperationService} from "./operation.service";

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.css']
})
export class OperationComponent implements OnInit {

  /**
   * 应用数据
   * @type {Array}
   */
  applications: SelectItem[] = [];

  /**
   * 应用ID
   */
  applicationId: string;

  /**
   * 选中的应用
   */
  selectedApp: string;

  /**
   * 选中的节点
   */
  selectedNode: TreeNode;

  /**
   * 树形数据
   * @type {Array}
   */
  tree: TreeNode[] = [];

  /**
   * 详细信息
   */
  detailData: Operation;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected operationService: OperationService) {
    this.detailData = new Operation();
  }

  refreshTree() {
    const url = 'operation/tree?applicationId=' + this.applicationId;
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
      });
  }

  ngOnInit(): void {
    this.applicationId = '';
    this.selectedApp = '';
    // 响应事件
    this.operationService.message$.subscribe(
      msg => {
        this.applicationId = this.selectedApp;
        this.refreshTree();
      }, error => {
        console.error('error: ' + error);
      }
    );

    this.refreshTree();
    this.applications.push({label: '请选择一个系统', value: ''});
    const url = 'application/all';
    this.httpService.findByParams(url).then(res => {
      for (let i = 0; i < res.length; i++) {
        this.applications.push({label: res[i]['label'], value: res[i]['id']});
      }
    });
  }

  nodeSelect(event) {
    const id = event.node.data.id;
    this.applicationId = event.node.data.applicationId;
    let parentLabel;
    if (event.node.parent == null) {
      parentLabel = '';
    } else {
      parentLabel = event.node.parent.label;
    }
    this.router.navigate(['panel',
      {
        id: id,
        parentLabel: parentLabel,
        applicationId: this.applicationId
      }], {
      relativeTo: this.route,
      skipLocationChange: true
    });
  }

  selectChange() {
    this.applicationId = this.selectedApp;
    this.refreshTree();
  }

}
