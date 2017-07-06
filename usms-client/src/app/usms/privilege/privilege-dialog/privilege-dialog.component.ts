import {Component, OnInit, ViewChild, Output, EventEmitter} from '@angular/core';
import {BaseDetail} from '../../../shared/util/base-detail';
import {NgForm} from '@angular/forms';
import {Location} from '@angular/common';
import {HttpService} from '../../../core/service/http.service';
import {ActivatedRoute} from '@angular/router';
import {Privilege} from '../privilege';

@Component({
  selector: 'app-privilege-dialog',
  templateUrl: './privilege-dialog.component.html',
  styleUrls: ['./privilege-dialog.component.css']
})
export class PrivilegeDialogComponent extends BaseDetail<Privilege> implements OnInit {

  sourceRoles: any = [];
  targetRoles: any = [];

  // 表单验证
  @ViewChild('reForm') reForm: NgForm;


  // 双向绑定 onSaved
  @Output() onSaved = new EventEmitter();

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Privilege();
  }


  save() {
    this.display = false;
    const url = 'privilege/saveOrUpdate';
    this.detailData.roleIds = '';
    this.detailData.roleIds = this.targetRoles.map(role => role.id).join(',');
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功更新' + this.detailData.name
        });
        this.onSaved.emit('refreshTable');
      });
  }

  ngOnInit(): void {
  }

  // 取消或保存
  doExecute(event) {
    if (event.goBack) {
      this.goBack();
    }
    if (event.save) {
      this.save();
    }
  }

  // 角色选择器的初始化
  rolesInit(id: any) {
    const targetUrl = 'privilege/roles/target';
    const sourceUrl = 'privilege/roles/source';
    if (id === 'null') {
      id = '';
    }
    const params = {privilegeId: id};
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

  showDialog(type: string, id: any){
    this.display = true;
    const url = 'privilege/find';
    this.rolesInit(id);
    this.initDialog(url, type, id).then(res => {
      if (res === false) {
        this.detailData = new Privilege();
        this.detailData.enabled = 1;
      }
    });
  }

  goBack() {
    this.display = false;
  }

}

