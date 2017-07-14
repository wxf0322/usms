import {Component, OnInit, Output, EventEmitter, Input} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {BaseDetail} from "../../../shared/util/base-detail";
import {HttpService} from "../../../core/service/http.service";
import {TreeNode} from "primeng/components/common/api";
import {TreeUtil} from "../../../shared/util/tree-util";
import {TreeData} from "../../../shared/util/tree-data";


@Component({
  selector: 'app-grid-associate-dialog',
  templateUrl: './grid-associate-dialog.component.html',
  styleUrls: ['./grid-associate-dialog.component.css']
})
export class GridAssociateDialogComponent extends BaseDetail<any> implements OnInit {

  /**
   * 用户id
   */
  userId: string;

  /**
   * 弹框网格树，已选中数据
   */
  selectedNodes: TreeNode[] = [];

  /**
   * 弹框网格树
   */
  tree: TreeNode[];

  /**
   * 绑定事件
   * @type {EventEmitter}
   */
  @Output() onSaved = new EventEmitter();

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
  }

  ngOnInit(): void {
    this.treeInit();
  }

  setSelectedNodes() {
    this.selectedNodes = [];
    const url = 'user/grids';
    const params = {userId: this.userId};
    this.httpService.findByParams(url, params)
      .then(res => TreeUtil.setSelection(this.tree, this.selectedNodes, res));
  }

  treeInit() {
    this.tree = [];
    const url = 'grid/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
        this.tree[0].expanded = true;
      });
  }

  showDialog(id: string) {
    this.display = true;
    this.userId = id;
    this.setSelectedNodes();
  }

  goBack() {
    this.display = false;
  }

  save() {
    const gridCodes = this.selectedNodes.map(node => node.data.code).join(',');
    const params = {userId: this.userId, gridCodes: gridCodes};
    const url = 'grid/updateGrids';
    this.httpService.executeByParams(url, params).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功更新，该用户所属网格'
        });
        this.goBack();
        this.onSaved.emit('refreshTable');
      });
  }

}
