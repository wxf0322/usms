import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {HttpService} from "../../../core/service/http.service";
import {Role} from "../role";
import {Privilege} from "../../privilege/privilege";
import {TreeNode} from "primeng/components/common/api";


@Component({
  selector: 'app-role-detail',
  templateUrl: './role-detail.component.html',
  styleUrls: ['./role-detail.component.css']
})

export class RoleDetailComponent extends SimpleBaseDetailUtil<Role> implements OnInit {

  //筛选框资源
  sourcePrivileges: Privilege[] = [];
  targetPrivileges: Privilege[] = [];
  sourceUsers: any = [];
  targetUsers: any = [];
  //树资源
  tree: TreeNode[];

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
    this.privilegesInit();
    this.usersInit();
    const url = 'role/find';
    this.init(url);
  }

  goBack() {
    this.location.back();
  }

  save() {
    let url = 'role/saveOrUpdate';
    this.detailData.privilegeIds = '';
    for (let i in this.targetPrivileges) {
      this.detailData.privilegeIds =
        this.detailData.privilegeIds + this.targetPrivileges[i].id + ',';
    }
    this.detailData.userIds = '';
    for (let i in this.targetUsers) {
      this.detailData.userIds =
        this.detailData.userIds + this.targetUsers[i].ID + ',';
    }
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
    let id = this.route.snapshot.params['id'];
    let selectedUrl = 'role/privileges/selected';
    let unSelectedUrl = 'role/privileges/unselected';
    let params = {
      roleId: id
    };
    this.httpService.executeByParams(unSelectedUrl, params).then(
      res => {
        this.sourcePrivileges = res;
      }
    );
    this.httpService.executeByParams(selectedUrl, params).then(
      res => {
        this.targetPrivileges = res;
      }
    );
  }

  usersInit() {
    this.tree = [
      {
        "label": "Documents",
        "data": "Documents Folder",
        "expandedIcon": "fa-folder-open",
        "collapsedIcon": "fa-folder",
        "children": [{
          "label": "Work",
          "data": "Work Folder",
          "expandedIcon": "fa-folder-open",
          "collapsedIcon": "fa-folder",
          "children": [{
            "label": "Expenses.doc",
            "icon": "fa-file-word-o",
            "data": "Expenses Document"
          }, {"label": "Resume.doc", "icon": "fa-file-word-o", "data": "Resume Document"}]
        },
          {
            "label": "Home",
            "data": "Home Folder",
            "expandedIcon": "fa-folder-open",
            "collapsedIcon": "fa-folder",
            "children": [{"label": "Invoices.txt", "icon": "fa-file-word-o", "data": "Invoices for this month"}]
          }]
      }
    ];

    let id = this.route.snapshot.params['id'];
    let selectedUrl = 'role/users/selected';
    let unSelectedUrl = 'role/users/unselected';
    let params = {
      roleId: id
    };
    this.httpService.executeByParams(unSelectedUrl, params).then(
      res => {
        this.sourceUsers = res;
      }
    );
    this.httpService.executeByParams(selectedUrl, params).then(
      res => {
        this.targetUsers = res;
      }
    );
  }


}
