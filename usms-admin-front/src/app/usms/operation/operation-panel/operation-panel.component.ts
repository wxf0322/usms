import {Component, OnInit} from '@angular/core';
import {HttpService} from '../../../core/service/http.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ConfirmationService} from 'primeng/primeng';
import {Operation} from "../operation";
import {OperationService} from "../operation.service";

@Component({
  selector: 'app-operation-panel',
  templateUrl: './operation-panel.component.html',
  styleUrls: ['./operation-panel.component.css']
})
export class OperationPanelComponent implements OnInit {

  /**
   * 父操作名称
   */
  parentLabel: string;

  /**
   * 面板详细信息
   */
  detailData: Operation;

  /**
   * 应用ID
   */
  applicationId: string;

  constructor(protected router: Router,
              protected httpService: HttpService,
              protected route: ActivatedRoute,
              protected operationService: OperationService,
              protected confirmationService: ConfirmationService) {
    this.detailData = new Operation();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = this.detailData.id = params['id'];
      this.parentLabel = params['parentLabel'];
      this.applicationId = params['applicationId'];
      if (typeof (id) !== 'undefined') {
        const url = 'operation/find';
        this.httpService.findById(url, id).then(
          res => this.detailData = res
        );
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
   * @param applicationId
   */
  addTreeNode(type: string, parentId: number, applicationId: string) {
    if (this.validate()) {
      this.router.navigate(
        ['../detail', {type: type, parentId: parentId, applicationId: applicationId}],
        {relativeTo: this.route}
      );
    }
  }

  /**
   * 编辑树形节点
   * @param type
   * @param id
   */
  editTreeNode(type: string, id: number) {
    if (this.validate()) {
      this.router.navigate(['../detail', {type: type, id: id}], {relativeTo: this.route});
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
        const url = 'operation/delete';
        const param = {id: id};
        this.httpService.executeByParams(url, param).then(
          res => {
            if (res['success'] === true) {
              this.detailData = new Operation();
              this.httpService.setMessage({
                severity: 'success',
                summary: '操作成功',
                detail: '该树形节点删除成功！'
              });
              this.operationService.sendMessage('refresh');
            } else {
              this.httpService.setMessage({
                severity: 'error',
                summary: '操作失败',
                detail: '该操作存在子操作!'
              });
            }
          }
        );
      }
    });
  }

}
