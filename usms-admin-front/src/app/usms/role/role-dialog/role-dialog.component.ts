import {Component, OnInit, Output, ViewChild, EventEmitter} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Location} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {BaseDetail} from '../../../shared/util/base-detail';
import {HttpService} from '../../../core/service/http.service';
import {Role} from '../role';
import {Privilege} from '../../privilege/privilege';
import {TreeNode} from 'primeng/primeng';
import {TreeData} from '../../../shared/util/tree-data';
import {TreeUtil} from '../../../shared/util/tree-util';

@Component({
  selector: 'app-role-dialog',
  templateUrl: './role-dialog.component.html',
  styleUrls: ['./role-dialog.component.css']
})
export class RoleDialogComponent extends BaseDetail<Role> implements OnInit {

  /**
   * 未选中的权限
   * @type {Array}
   */
  sourcePrivileges: Privilege[] = [];

  /**
   * 已选中的权限
   * @type {Array}
   */
  targetPrivileges: Privilege[] = [];

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
   * 组织机构树
   */
  tree: TreeNode[];

  /**
   * 角色id
   */
  roleId: any;

  /**
   * 绑定事件
   * @type {EventEmitter}
   */
  @Output() onSaved = new EventEmitter();

  /**
   * 表单验证
   */
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Role();

  }

  ngOnInit() {
  }

  showDialog(type: string, roleId: string) {
    this.roleId = roleId;
    this.display = true;
    this.privilegesInit(roleId);
    this.refreshTree();
    this.usersInit(roleId);
    const url = 'role/find';
    this.initDialog(url, type, roleId).then(res => {
      if (res == false) {
        this.detailData = new Role();
        this.detailData.enabled = 1;
      }
    });
  }

  goBack() {
    this.display = false;
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

  save() {
    this.display = false;
    const url = 'role/saveOrUpdate';
    this.detailData.privilegeIds = this.targetPrivileges.map(priv => priv.id).join(',');
    this.detailData.userIds = this.targetUsers.map(user => user.ID).join(',');
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '角色数据，' + this.detailData.label + '，更新或保存成功'
        });
        this.onSaved.emit('refreshTable');
      });
  }

  privilegesInit(roleId: string) {
    const targetUrl = 'role/privileges/target';
    const sourceUrl = 'role/privileges/source';
    const params = {roleId: roleId};
    this.httpService.executeByParams(sourceUrl, params).then(
      res => this.sourcePrivileges = res
    );
    this.httpService.executeByParams(targetUrl, params).then(
      res => this.targetPrivileges = res
    );
  }

  usersInit(roleId: string) {
    const targetUrl = 'role/users/target';
    const sourceUrl = 'role/users/source';
    const targetParams = {roleId: roleId};
    const sourceParams = {roleId: roleId, institutionId: null, key: ''};
    this.httpService.findByParams(sourceUrl, sourceParams).then(
      res => this.sourceUsers = res
    );
    this.httpService.findByParams(targetUrl, targetParams).then(
      res => this.targetUsers = res
    );
  }
}
