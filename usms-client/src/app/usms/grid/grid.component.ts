import {Component, OnInit, Renderer} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmationService, TreeNode} from 'primeng/primeng';
import {TreeData} from "../../shared/util/tree-data";
import {TreeUtil} from "../../shared/util/tree-util";
import {HttpService} from "../../core/service/http.service";
import {SimpleBaseUtil} from "../../shared/util/simple-base.util";
import {User} from "../user/user";

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent extends SimpleBaseUtil<any>  implements OnInit {


  /**
   * 树形节点
   */
  tree: TreeNode[];

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
  }

  ngOnInit(): void {
    const url = 'grid/tree';

    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
      });

    this.getDataByPage(0, this.page.size, this.filter);
  }

  deleteSelected() {
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: User) {
    let res = [];
    this.viewData = [{
      loginName: 'admin',
      name: 'admin'
    }];
  }

}
