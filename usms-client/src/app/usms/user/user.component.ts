import {Component, OnInit, Renderer} from '@angular/core';
import {TreeNode} from 'primeng/primeng';
import {SimpleBaseUtil} from '../../shared/util/simple-base.util';
import {ConfirmationService} from 'primeng/primeng';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../../core/service/http.service';
import {GlobalVariable} from '../../shared/global-variable';
import {TreeData} from '../../shared/util/tree-data';
import {TreeUtil} from '../../shared/util/tree-util';
import {SelectItem} from 'primeng/components/common/api';
import {User} from '../role/user-allocation/users';

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

  /**
   * 选中的树
   */
  selectedNode: TreeNode;

  /**
   * 查询相关的三个变量
   */
  selects: SelectItem[];

  /**
   * 选中查询
   */
  selectKey: string;

  /**
   * 查询关键词
   */
  key: string;

  /**
   * 用户所选的机构id
   */
  institutionId: string;

  /**
   *移入用户相关的隐藏和显示
   */
  userDisplay = false;

  /**
   * 已选中用户
   */
  sourceUsers: User[] = [];

  /**
   * 待选中用户
   * @type {Array}
   */
  targetUsers: User[] = [];

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
    this.selects = [];
    this.selects.push({label: '请选择查询条件', value: 0});
    this.selects.push({label: '用户帐号', value: 1});
    this.selects.push({label: '用户姓名', value: 2});
  }

  ngOnInit(): void {
    this.refreshTree();
    this.getDataByPage(0, this.page.size, this.filter);
  }

  deleteSelected() {
    const url = GlobalVariable.BASE_URL + 'user/delete';
    this.delete(url, 'id');
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
    const url = GlobalVariable.BASE_URL + 'user/list';
    this.httpService.findByPage(url, currentPage, rowsPerPage, null).then(
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

  resetPassword(id: string) {
    const url = GlobalVariable.BASE_URL + 'user/password/reset';
    const params = {id: id};
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
    let url = GlobalVariable.BASE_URL + 'user/list';
    if (this.selectKey === '1') {
      url = GlobalVariable.BASE_URL + 'user/list?loginName=' + this.key;
    } else if (this.selectKey === '2') {
      url = GlobalVariable.BASE_URL + 'user/list?name=' + this.key;
    }
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

