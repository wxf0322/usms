import {Component, OnInit, Renderer} from '@angular/core';
import {TreeNode} from 'primeng/primeng';
import {SimpleBaseUtil} from '../../shared/util/simple-base.util';
import {ConfirmationService} from 'primeng/primeng';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../../core/service/http.service';
import {GlobalVariable} from '../../shared/global-variable';
import {TreeData} from '../../shared/util/tree-data';
import {TreeUtil} from '../../shared/util/tree-util';
import 'rxjs/add/operator/switchMap';


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent extends SimpleBaseUtil<any> implements OnInit {

  tree: TreeNode[];

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
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

  reset() {
  }

}

