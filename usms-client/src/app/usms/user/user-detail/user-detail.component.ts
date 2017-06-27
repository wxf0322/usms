import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Location} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {BaseDetail} from '../../../shared/util/base-detail';
import {HttpService} from '../../../core/service/http.service';
import {User} from '../user';
import {Role} from '../../role/role';
import {TreeData} from '../../../shared/util/tree-data';
import {TreeUtil} from '../../../shared/util/tree-util';
import {TreeNode} from 'primeng/primeng';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent extends BaseDetail<any> implements OnInit {

  // 表单验证
  @ViewChild('reForm') reForm: NgForm;

  date: Date;

  sourceRoles: Role[] = [];

  targetRoles: Role[] = [];

  institutionName: string;

  tree: TreeNode[] = [];

  /**
   * 回填机构树的数据
   */
  selectedNodes: TreeNode[] = [];

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new User();
    this.detailData.enabled = 1;
    this.detailData.sex = 1;
  }

  refreshTree() {
    const url = 'institution/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
        this.tree[0].expanded = true;
        this.setInstitutions();
      });
  }

  ngOnInit(): void {
    // 传入组织机构名称
    this.institutionName = this.route.snapshot.params['institutionName'];
    if (this.institutionName === 'undefined') {
      this.institutionName = '';
    }
    const url = 'user/find';
    this.init(url).then(
      res => {
        if (this.detailData.birthday != null) {
          this.date = new Date(this.detailData.birthday);
        } else {
          this.date = new Date();
        }
      });
    this.refreshTree();
    this.roleInit();
  }

  goBack() {
    this.location.back();
  }

  save() {
    this.detailData.birthday = this.date.getTime();
    this.detailData.roleIds = this.targetRoles.map(role => role.id).join(',');
    this.detailData.institutionIds = this.selectedNodes.map(node => node.data.id).join(',');

    const url = 'user/saveOrUpdate';
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

  roleInit() {
    let id = this.route.snapshot.params['id'];
    let targetUrl = 'user/roles/target';
    let sourceUrl = 'user/roles/source';
    if (id === 'null') {
      id = '';
    }
    let params = {userId: id};
    this.httpService.executeByParams(sourceUrl, params).then(
      res => this.sourceRoles = res
    );
    this.httpService.executeByParams(targetUrl, params).then(
      res => this.targetRoles = res
    );
  }

  setInstitutions() {
    let userId = this.route.snapshot.params['id'];
    const url = 'user/institutions';
    const params = {userId: userId};
    this.httpService.findByParams(url, params)
      .then(res => {
        // 回填已选中的网格数据
        TreeUtil.setSelection(this.tree, this.selectedNodes, res);
      });
  }

}
