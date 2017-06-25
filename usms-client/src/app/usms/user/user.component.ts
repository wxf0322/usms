import {Component, OnInit, Renderer} from '@angular/core';
import {TreeNode} from 'primeng/primeng';
import {BaseTable} from '../../shared/util/base-table';
import {ConfirmationService} from 'primeng/primeng';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../../core/service/http.service';
import {TreeData} from '../../shared/util/tree-data';
import {TreeUtil} from '../../shared/util/tree-util';

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
  selectedNode: TreeNode[];

  /**
   * 用户所选的机构id
   */
  institutionId: string;

  /**
   * 当前所属的机构名称
   */
  institutionName: string;


  height: number;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
  }

  ngOnInit(): void {
    this.refreshTree();
    this.filter = '';
    this.getDataByPage(this.page.number, this.page.size, this.filter);
  }

  deleteSelected() {
    const url = 'user/delete';
    this.delete(url, 'id');
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: string) {
    const url = 'user/list?key=' + this.filter;
    this.httpService.findByPage(url, currentPage, rowsPerPage, filter).then(
      res => {
        return this.setData(res);
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
      });
  }

  resetPassword() {
    const url = 'user/password/reset';
    const params = {
      ids: this.selectedData.map(data => data['id']).join(',')
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

  query() {
    const url = 'user/list?key=' + this.filter;
    let params = {institutionId: this.institutionId};
    this.httpService.findByPage(url, 0, this.page.size, params).then(
      res => {
        return this.setData(res);
      }
    );
  }

  gotoUserDetail(type: string, id: string) {
    this.router.navigate(['user-detail', {
      type: type,
      id: id,
      institutionId: this.institutionId,
      institutionName: this.institutionName
    }], {relativeTo: this.route});
  }

  /**
   * 单击树形节点
   * @param event
   */
  nodeSelect(event) {
    this.institutionName = event.node.label;
    let url = 'user/list?key=' + this.filter;
    if (event.node.data.parentId != 0) {
      this.institutionId = event.node.data.id;
    } else {
      this.institutionId = null;
    }
    let params = {
      institutionId: this.institutionId
    };
    this.httpService.findByPage(url, 0, this.page.size, params).then(
      res => this.setData(res)
    );
  }

}

