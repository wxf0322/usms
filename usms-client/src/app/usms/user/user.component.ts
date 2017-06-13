import {Component, OnInit, Renderer} from '@angular/core';
import {TreeNode} from 'primeng/primeng';
import {SimpleBaseUtil} from '../../shared/util/simple-base.util';
import {ConfirmationService} from 'primeng/primeng';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../../core/service/http.service';
import {GlobalVariable} from '../../shared/global-variable';
import {TreeData} from '../../shared/util/tree-data';
import {TreeUtil} from '../../shared/util/tree-util';
import {User} from './user';



@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent extends SimpleBaseUtil<any> implements OnInit {
  /**
   *组织机构树
   */
  tree: TreeNode[];

  sourceUsers: User[] = [];
  targetUsers: User[] = [];

  /**
   * 选中的节点
   */
  selectedNode: TreeNode[];

  /**
   * 用户所选的机构id
   */
  institutionId: string;

  /**
   *移入用户相关的隐藏和显示
   */
  userDisplay = false;

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
    const url = GlobalVariable.BASE_URL + 'user/delete';
    this.delete(url, 'id');
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: string) {
    const url = GlobalVariable.BASE_URL + 'user/list?key=' + this.filter;
    this.httpService.findByPage(url, currentPage, rowsPerPage, filter).then(
      res => {
        return this.setData(res);
      }
    );
  }

  refreshTree() {
    const url = GlobalVariable.BASE_URL + 'institution/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
      });
  }

  resetPassword() {
    const url = GlobalVariable.BASE_URL + 'user/password/reset';
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
    const url = GlobalVariable.BASE_URL + 'user/list?key=' + this.filter;
    this.httpService.findByPage(url, 0, this.page.size, null).then(
      res => {
        return this.setData(res);
      }
    );
  }



  gotoUserDetail(type: string, id: string) {
    this.router.navigate(['user-detail', {
      type: type,
      id: id,
      institutionId: this.institutionId
    }], {relativeTo: this.route});
  }

  /**
   * 单击树形节点
   * @param event
   */
  nodeSelect(event) {
    this.institutionId = event.node.data.id;
    const url = GlobalVariable.BASE_URL + 'user/list?institutionName=' + event.node.data.name;
    this.httpService.findByPage(url, 0, this.page.size, null).then(
      res => {
        return this.setData(res);
      }
    );
  }

  moveUsers() {
    this.userDisplay = true;
  }


}

