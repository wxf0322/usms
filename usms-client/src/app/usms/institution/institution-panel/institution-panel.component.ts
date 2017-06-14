import {Component, OnInit} from '@angular/core';
import {Institution} from '../institution';
import {HttpService} from '../../../core/service/http.service';
import {ActivatedRoute, Router} from '@angular/router';
import {InstitutionService} from '../institution.service';
import {ConfirmationService} from 'primeng/primeng';

@Component({
  selector: 'app-institution-panel',
  templateUrl: './institution-panel.component.html',
  styleUrls: ['./institution-panel.component.css']
})
export class InstitutionPanelComponent implements OnInit {

  /**
   * 父机构名称
   */
  parentLabel: string;

  /**
   * 面板详细信息
   */
  detailData: Institution;

  constructor(protected router: Router,
              protected httpService: HttpService,
              protected route: ActivatedRoute,
              protected institutionService: InstitutionService,
              protected confirmationService: ConfirmationService) {
    this.detailData = new Institution();
  }

  /**
   * 初始化操作
   */
  ngOnInit() {
    this.route.params
      .subscribe(params => {
        const id = this.detailData.id = params['id'];
        this.parentLabel = params['parentLabel'];
        if (typeof (id) !== 'undefined') {
          const url = 'institution/find';
          this.httpService.findById(url, id).then(
            res => {
              this.detailData = res;
            });
        }
      });
  }

  /**
   * 判断操作是否有效
   * @returns {boolean}
   */
  validate(): boolean {
    if (typeof (this.detailData.id) === 'undefined') {
      this.httpService.setMessage({
        severity: 'error',
        summary: '操作失败',
        detail: '请先选中一个组织机构！'
      });
      return false;
    }
    return true;
  }

  /**
   * 新增树形节点
   * @param type
   * @param parentId
   */
  addTreeNode(type: string, parentId: number) {
    if (this.validate()) {
      this.router.navigate(['../detail', {
        type: type,
        parentId: parentId,
        parentLabel: this.parentLabel
      }], {relativeTo: this.route});
    }
  }

  /**
   * 编辑树形节点
   * @param type
   * @param id
   */
  editTreeNode(type: string, id: number) {
    if (this.validate()) {
      this.router.navigate(['../detail', {
        type: type,
        id: id,
        parentLabel: this.parentLabel
      }], {relativeTo: this.route});
    }
  }

  /**
   * 删除树形节点
   * @param id
   */
  deleteTreeNode(id: number) {
    if (!this.validate()) {
      return;
    }
    this.confirmationService.confirm({
      message: '你确定要删除该节点的数据？',
      header: '提示',
      accept: () => {
        const url = 'institution/delete';
        const param = {id: id};
        this.httpService.executeByParams(url, param).then(
          res => {
            if (res['success'] === true) {
              this.httpService.setMessage({
                severity: 'success',
                summary: '操作成功',
                detail: '该机构部门删除成功！'
              });
              this.institutionService.sendMessage('refreshTree');
            } else {
              this.httpService.setMessage({
                severity: 'error',
                summary: '操作失败',
                detail: '该机构存在子机构，或者该机构存在用户，所以删除失败！'
              });
            }
          }
        );
      }
    });
  }

}
