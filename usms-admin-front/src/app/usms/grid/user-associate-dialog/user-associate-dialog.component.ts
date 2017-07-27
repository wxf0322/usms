import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {BaseDetail} from "../../../shared/base-component/base-detail";
import {HttpService} from "../../../core/service/http.service";
import {TreeNode} from "primeng/components/common/api";
import {TreeData} from "../../../shared/api";
import {TreeUtil} from "../../../shared/util/tree-util";

@Component({
  selector: 'app-user-associate-dialog',
  templateUrl: './user-associate-dialog.component.html',
  styleUrls: ['./user-associate-dialog.component.css']
})
export class UserAssociateDialogComponent extends BaseDetail<any> implements OnInit {

  /**
   * 组织机构树
   */
  tree: TreeNode[];

  /**
   * 未选中的用户
   * @type {Array}
   */
  sourceUsers: any = [];

  /**
   * 已选中的用户
   * @type {Array}
   */
  targetUsers: any = [];

  /**
   * 网格编码
   */
  gridCode: any;

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

  ngOnInit() {
  }

  showDialog(code: string) {
    this.gridCode = code;
    this.display = true;
    this.refreshTree();
    this.usersInit(code);
  }

  goBack() {
    this.display = false;
  }

  save() {
    let url = "grid/updateUsers";
    let userIds = this.targetUsers.map(user => user.ID).join(',');
    let params = {
      gridCode: this.gridCode,
      userIds: userIds
    };
    this.httpService.executeByParams(url, params).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          detail: '操作成功',

        });
        this.goBack();
        this.onSaved.emit("refreshTable");
      }
    );
  }

  refreshTree() {
    const url = 'institution/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
        this.tree[0].expanded = true;
      });
  }

  usersInit(gridCode: string) {
    let targetUrl = 'grid/users/target';
    let sourceUrl = 'grid/users/source';
    let params = {
      gridCode: gridCode
    };
    this.httpService.executeByParams(sourceUrl, params).then(
      res => this.sourceUsers = res
    );
    this.httpService.executeByParams(targetUrl, params).then(
      res => this.targetUsers = res
    );
  }

}
