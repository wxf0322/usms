import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {BaseDetail} from "../../../shared/util/base-detail";
import {HttpService} from "../../../core/service/http.service";
import {TreeNode} from "primeng/components/common/api";
import {TreeData} from "../../../shared/util/tree-data";
import {TreeUtil} from "../../../shared/util/tree-util";

@Component({
  selector: 'app-user-associate',
  templateUrl: './user-associate.component.html',
  styleUrls: ['./user-associate.component.css']
})
export class UserAssociateComponent extends BaseDetail<any> implements OnInit {

  /**
   * 网格树
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
          summary: '操作成功',
          detail: '所属用户，更新成功'
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

  showDialog(code: string) {
    this.gridCode = code;
    this.display = true;
    this.refreshTree();
    this.usersInit(code);
  }
}
