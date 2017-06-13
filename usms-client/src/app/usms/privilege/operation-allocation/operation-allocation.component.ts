import {Component, OnInit} from '@angular/core';
import {SimpleBaseDetailUtil} from '../../../shared/util/simple-base-detail.util';
import {Location} from '@angular/common';
import {HttpService} from '../../../core/service/http.service';
import {ActivatedRoute} from '@angular/router';
import {TreeNode, SelectItem} from 'primeng/primeng';
import {GlobalVariable} from '../../../shared/global-variable';
import {TreeData} from '../../../shared/util/tree-data';
import {TreeUtil} from '../../../shared/util/tree-util';

@Component({
  selector: 'app-operation-allocation',
  templateUrl: './operation-allocation.component.html',
  styleUrls: ['./operation-allocation.component.css']
})
export class OperationAllocationComponent extends SimpleBaseDetailUtil<any> implements OnInit {


  applications: SelectItem[];

  tree: TreeNode[];

  /**
   * 已经选中的节点
   */
  selectedNodes: TreeNode[];

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.applications = [
      {label: '老年证系统', value: {id: 1}},
      {label: '综治系统', value: {id: 2}},
      {label: '电子证照系统', value: {id: 3}},
    ];
  }

  goBack() {
    this.location.back();
  }

  save() {
    let selectedIds = this.selectedNodes.map(function (node) {
      return node.data['id'];
    });
    let operationIds = selectedIds.join(',');
    let privilegeId = this.route.snapshot.params['id'];
    let params = {privilegeId: privilegeId, operationIds: operationIds};
    let url = GlobalVariable.BASE_URL + 'privilege/operations/update';
    this.httpService.findByParams(url, params).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '操作授权成功！'
        });
        this.goBack();
      }
    );
  }

  setSelected() {
    const id = this.route.snapshot.params['id'];
    const selectedUrl = GlobalVariable.BASE_URL + 'privilege/operations';
    const params = {privilegeId: id};
    this.selectedNodes = [];
    this.httpService.findByParams(selectedUrl, params)
      .then(res => {
        TreeUtil.setSelection(this.tree, this.selectedNodes, res);
      });
  }

  refreshTree() {
    const url = GlobalVariable.BASE_URL + 'operation/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
        this.setSelected();
      });
  }


  ngOnInit(): void {
    this.refreshTree();
  }

}

