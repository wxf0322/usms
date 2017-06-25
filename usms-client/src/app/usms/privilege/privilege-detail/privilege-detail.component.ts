import {Component, OnInit, ViewChild} from '@angular/core';
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {NgForm} from "@angular/forms";
import {Location} from '@angular/common';
import {HttpService} from "../../../core/service/http.service";
import {ActivatedRoute} from "@angular/router";
import {Privilege} from "../privilege";

@Component({
  selector: 'app-privilege-detail',
  templateUrl: './privilege-detail.component.html',
  styleUrls: ['./privilege-detail.component.css']
})
export class PrivilegeDetailComponent extends SimpleBaseDetailUtil<Privilege> implements OnInit {
  sourceRoles: any = [];
  targetRoles: any = [];

  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Privilege();
    this.detailData.enabled = 1;
  }

  goBack() {
    this.location.back();
  }

  save() {
    let url = 'privilege/saveOrUpdate';
    this.detailData.roleIds = '';
    for (let i in this.targetRoles) {
      this.detailData.roleIds =
        this.detailData.roleIds + this.targetRoles[i].id + ',';
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

  ngOnInit(): void {
    let url = 'privilege/find';
    this.init(url);
    this.rolesInit();
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

  //角色选择器的初始化
  rolesInit() {
    let id = this.route.snapshot.params['id'];
    let targetUrl = 'privilege/roles/target';
    let sourceUrl = 'privilege/roles/source';
    if (id == 'null') {
      id = '';
    }
    let params = {
      privilegeId: id
    };
    this.httpService.executeByParams(sourceUrl, params).then(
      res => {
        this.sourceRoles = res;
      }
    );
    this.httpService.executeByParams(targetUrl, params).then(
      res => {
        this.targetRoles = res;
      }
    );
  }

}

