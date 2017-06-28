import {Component, OnInit, ViewChild} from '@angular/core';
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
  selector: 'app-role-detail',
  templateUrl: './role-detail.component.html',
  styleUrls: ['./role-detail.component.css']
})

export class RoleDetailComponent extends BaseDetail<Role> implements OnInit {

  //筛选框资源
  sourcePrivileges: Privilege[] = [];
  targetPrivileges: Privilege[] = [];
  sourceUsers: any = [];
  targetUsers: any = [];

  //树资源
  tree: TreeNode[];

  //角色id
  roleId: any = this.route.snapshot.params['id'];
  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Role();
    this.detailData.enabled = 1;
  }

  ngOnInit() {
    this.refreshTree();
    this.privilegesInit();
    this.usersInit();
    const url = 'role/find';
    this.init(url);
  }

  goBack() {
    this.location.back();
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
    const url = 'role/saveOrUpdate';
    this.detailData.privilegeIds = this.targetPrivileges.map(priv => priv.id).join(',');
    this.detailData.userIds = this.targetUsers.map(user => user.ID).join(',');

    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功更新' + this.detailData.name
        });
        this.goBack();
      });
  }

  privilegesInit() {
    const id = this.route.snapshot.params['id'];
    const targetUrl = 'role/privileges/target';
    const sourceUrl = 'role/privileges/source';
    const params = {roleId: id};
    this.httpService.executeByParams(sourceUrl, params).then(
      res => this.sourcePrivileges = res
    );
    this.httpService.executeByParams(targetUrl, params).then(
      res => this.targetPrivileges = res
    );
  }

  usersInit() {
    const id = this.route.snapshot.params['id'];
    const targetUrl = 'role/users/target';
    const sourceUrl = 'role/users/source';
    const params = {roleId: id};
    this.httpService.executeByParams(sourceUrl, params).then(
      res => this.sourceUsers = res
    );
    this.httpService.executeByParams(targetUrl, params).then(
      res => this.targetUsers = res
    );
  }

}
