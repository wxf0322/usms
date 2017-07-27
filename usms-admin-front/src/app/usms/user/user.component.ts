import {Component, OnInit, ViewChild, Renderer} from '@angular/core';
import {TreeNode} from 'primeng/primeng';
import {BaseTable} from '../../shared/base-component/base-table';
import {ConfirmationService} from 'primeng/primeng';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../../core/service/http.service';
import {TreeData} from '../../shared/api';
import {TreeUtil} from '../../shared/util/tree-util';
import {UserDialogComponent} from './user-dialog/user-dialog.component';
import {StringUtil} from "../../shared/util/string-util";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent extends BaseTable<any> implements OnInit {

  /**
   *组织机构树
   */
  tree: TreeNode[];

  /**
   * 选中的节点
   */
  selectedNode: TreeNode;

  /**
   * 用户所选的机构id
   */
  institutionId: number;

  /**
   * 当前所属的机构名称
   */
  institutionName: string;

  /**
   * 树形节点查询关键字
   */
  queryWord: string;

  @ViewChild(UserDialogComponent)
  userDialog: UserDialogComponent;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
  }

  ngOnInit(): void {
    this.filter = '';
    this.refreshTree();
    this.getDataByPage(this.page.number, this.page.size, this.filter);
  }

  deleteSelected() {
    const url = 'user/delete';
    this.delete(url, 'id');
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
    const url = 'user/list';
    const params = {
      key: StringUtil.trim(this.filter),
      institutionId: this.institutionId
    };
    this.httpService.findByPage(url, currentPage, rowsPerPage, params).then(
      res => this.setData(res)
    );
  }

  queryNode() {
    this.selectedNode = TreeUtil.findNodesByLabel(this.tree, StringUtil.trim(this.queryWord));
    this.institutionName = this.selectedNode.label;
    const url = 'user/list';
    if (this.selectedNode.data.parentId !== 0) {
      this.institutionId = this.selectedNode.data.id;
    } else {
      this.institutionId = null;
    }
    this.page.number = 0;
    this.page.size = 10;
    const params = {
      key: StringUtil.trim(this.filter),
      institutionId: this.institutionId
    };
    this.httpService.findByPage(url, this.page.number, this.page.size, params).then(
      res => this.setData(res)
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

  resetPassword() {
    const url = 'user/password/reset';
    const params = {
      userIds: this.selectedData.map(data => data['id']).join(',')
    };
    this.httpService.executeByParams(url, params).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '重置密码成功，默认密码为：123456'
        });
      }
    );
  }

  showDialog(type: string, id: string) {
    this.userDialog.showDialog(type, id, this.institutionId, this.institutionName);
  }

  query() {
    const params = {institutionId: this.institutionId};
    this.getDataByPage(0, this.page.size, params);
  }

  /**
   * 单击树形节点
   * @param event
   */
  nodeSelect(event) {
    this.institutionName = event.node.label;
    const url = 'user/list';
    if (event.node.data.parentId !== 0) {
      this.institutionId = event.node.data.id;
    } else {
      this.institutionId = null;
    }
    this.page.number = 0;
    this.page.size = 10;
    const params = {
      key: StringUtil.trim(this.filter),
      institutionId: this.institutionId
    };
    this.httpService.findByPage(url, this.page.number, this.page.size, params).then(
      res => this.setData(res)
    );
  }

  onSaved(event) {
    const params = {institutionId: this.institutionId};
    this.getDataByPage(this.page.number, this.page.size, params);
  }

}

