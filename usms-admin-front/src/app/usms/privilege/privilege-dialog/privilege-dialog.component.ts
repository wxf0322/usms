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

  /**
   * 未选中的角色
   * @type {Array}
   */
  sourceRoles: any = [];

  /**
   * 已选中的角色
   * @type {Array}
   */
  targetRoles: any = [];

  /**
   * 表单验证
   */
  @ViewChild('reForm') reForm: NgForm;

  /**
   * 绑定事件
   * @type {EventEmitter}
   */
  @Output() onSaved = new EventEmitter();

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Privilege();
  }

  ngOnInit(): void {
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
          detail: '操作成功',
        });
        this.onSaved.emit('refreshTable');
      });
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
      res => this.sourceRoles = res
    );
    this.httpService.executeByParams(targetUrl, params).then(
      res => this.targetRoles = res
    );
  }

  showDialog(type: string, id: any) {
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

  // 取消或保存
  doExecute(event) {
    if (event.goBack) {
      this.goBack();
    }
    if (event.save) {
      this.save();
    }
  }

  goBack() {
    this.display = false;
  }

}

