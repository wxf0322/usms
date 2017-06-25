import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {HttpService} from "../../../core/service/http.service";
import {TreeNode} from "primeng/components/common/api";
import {TreeData} from "../../../shared/util/tree-data";
import {TreeUtil} from "../../../shared/util/tree-util";

@Component({
  selector: 'app-user-associate',
  templateUrl: './user-associate.component.html',
  styleUrls: ['./user-associate.component.css']
})
export class UserAssociateComponent extends SimpleBaseDetailUtil<any> implements OnInit {

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
  }

  tree: TreeNode[];
  sourceUsers: any = [];
  targetUsers: any = [];
  gridCode: any;

  ngOnInit() {
    this.refreshTree();
    this.usersInit();
  }

  goBack() {
    this.location.back();
  }

  save() {
    let url = "grid/updateUsers";
    let userIds = '';
    for (let i in this.targetUsers) {
      userIds = userIds + this.targetUsers[i].ID + ',';
    }
    let params = {
      gridCode: this.gridCode,
      userIds: userIds
    };
    this.httpService.executeByParams(url, params).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功更新'
        });
        this.goBack();
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

  usersInit() {
    this.gridCode = this.route.snapshot.params['code'];
    let targetUrl = 'grid/users/target';
    let sourceUrl = 'grid/users/source';
    let params = {
      gridCode: this.gridCode
    };
    this.httpService.executeByParams(sourceUrl, params).then(
      res => this.sourceUsers = res
    );
    this.httpService.executeByParams(targetUrl, params).then(
      res => this.targetUsers = res
    );
  }
}
