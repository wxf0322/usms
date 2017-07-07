import {Component, OnInit,Output,EventEmitter} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {BaseDetail} from "../../../shared/util/base-detail";
import {HttpService} from "../../../core/service/http.service";
import {TreeNode} from "primeng/components/common/api";
import {TreeData} from "../../../shared/util/tree-data";
import {TreeUtil} from "../../../shared/util/tree-util";

@Component({
  selector: 'app-user-associate',
  templateUrl: './user-associate.component.html',
  styleUrls: ['./user-associate.component.css']
})
export class UserAssociateComponent extends BaseDetail<any> implements OnInit {

  tree: TreeNode[];
  sourceUsers: any = [];
  targetUsers: any = [];
  gridCode: any;

  dialogDisplay:boolean =false;
  // 双向绑定 dialogDisplay
  @Output() onSaved = new EventEmitter();

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
  }

  ngOnInit() {
  }

  goBack() {
    this.location.back();
  }

  save() {
    this.dialogDisplay = false;
    let url = "grid/updateUsers";
    let userIds = this.targetUsers.map(user => user.ID).join(',');
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
        this.onSaved.emit("refreshTable");
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
        this.tree[0].expanded = true;
      });
  }

  usersInit(gridCode:string) {
    let targetUrl = 'grid/users/target';
    let sourceUrl = 'grid/users/source';
    let params = {
      gridCode: gridCode
    };
    this.httpService.executeByParams(sourceUrl, params).then(
      res => this.sourceUsers = res
    );
    this.httpService.executeByParams(targetUrl, params).then(
      res => this.targetUsers = res
    );
  }

  showDialog(code:string){
    this.gridCode = code;
    this.dialogDisplay = true;
    this.refreshTree();
    this.usersInit(code);
  }
}
