/**
 * Created by thinkpad on 2017-5-16.
 */
import {Component, OnInit, ViewChild} from "@angular/core";
import {User} from "./users";
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {Location} from '@angular/common';
import {HttpService} from "../../../core/service/http.service";
import {ActivatedRoute} from "@angular/router";
import {TreeNode} from "primeng/primeng";
import {SelectItem} from "primeng/components/common/api";
import {GlobalVariable} from "../../../shared/global-variable";


@Component({
  selector: "app-user-allocation",
  templateUrl: "./user-allocation.component.html",
  styleUrls: ["./user-allocation.component.css"]
})

export class UserAllocationComponent extends SimpleBaseDetailUtil<User> implements OnInit {
  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
  }

  goBack() {
    this.location.back();
  }

  save() {
    let id=this.route.snapshot.params['id'];
    let saveUrl = GlobalVariable.BASE_URL + 'role/users/update';
    let ids='';
    for(var i in this.targetUsers){
      ids=ids+this.targetUsers[i].ID+',';
    }
    let params = {
      roleId: id,
      userIds:ids
    };
    this.httpService.executeByParams(saveUrl, params).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功更新'
        });
        this.goBack();
      });
  }

  clear() {
    this.selectedType = null;
  }
  //下拉框资源
  roles: SelectItem[];
  selectedRole: string;
  //筛选框资源
  sourceUsers: any;
  targetUsers: any;
  //树资源
  tree: TreeNode[];
  types: SelectItem[];

  selectedType: string;
  ngOnInit() {
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

    this.roles = [];
    this.roles.push({label:'New York', value:{id:1, name: 'New York', code: 'NY'}});
    this.roles.push({label:'Rome', value:{id:2, name: 'Rome', code: 'RM'}});
    this.roles.push({label:'London', value:{id:3, name: 'London', code: 'LDN'}});
    this.roles.push({label:'Istanbul', value:{id:4, name: 'Istanbul', code: 'IST'}});
    this.roles.push({label:'Paris', value:{id:5, name: 'Paris', code: 'PRS'}});

    let id = this.route.snapshot.params['id'];
    let selectedUrl = GlobalVariable.BASE_URL + 'role/users/selected';
    let unSelectedUrl = GlobalVariable.BASE_URL + 'role/users/unselected';
    let params = {
      roleId: id
    };
    this.httpService.executeByParams(unSelectedUrl, params).then(
      res => {this.sourceUsers = res;}
    );
    this.httpService.executeByParams(selectedUrl, params).then(
      res => {this.targetUsers = res;}
    );

  }

  //取消或保存
  doExecute(event) {
    if (event.goBack) {
      this.goBack();
    }
    if (event.save) {
      this.save();
    }
  }
}




