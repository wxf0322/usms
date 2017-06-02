import {Component, OnInit, Renderer} from '@angular/core';
import {ConfirmationService, MenuItem, SelectItem} from "primeng/primeng";
import {CmUnit} from "./cm-unit";
import {ActivatedRoute, Router} from "@angular/router";
import 'rxjs/add/operator/switchMap';
import {HttpService} from "../../../core/service/http.service";
import {SimpleBaseUtil} from "../../../shared/util/simple-base.util";

@Component({
  selector: 'app-cm-unit',
  templateUrl: './cm-unit.component.html',
  styleUrls: ['./cm-unit.component.css']
})
export class CmUnitComponent extends SimpleBaseUtil<CmUnit> implements OnInit {

  orgTypes: SelectItem[]; // 机构类型

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer:Renderer) {
    super(router, route, httpService, confirmationService,renderer);
  }

  ngOnInit() {
    this.initBtn();

    this.orgTypes = [];
    this.orgTypes.push({label: '- 机构类型 -', value: null});
    this.orgTypes.push({label: '综治委', value: 1});
    this.orgTypes.push({label: '综治办', value: 2});
    this.orgTypes.push({label: '综治工作中心', value: 3});
    this.orgTypes.push({label: '政法委', value: 4});

    this.filter = new CmUnit();
    this.getDataByPage(0, this.page.size, this.filter);
  }

  /*功能按钮初始化*/
  initBtn() {
    //右击操作
    this.items = [
      {
        label: '编辑', icon: 'fa-edit', command: (event) => {
        if (this.selectedData.length == 1) {
          this.gotoDetail('edit', this.selectedData[0].orgId)
        } else {
          this.warning('请选择一条数据！');
        }
      }
      },
      {
        label: '删除', icon: 'fa-close', command: (event) => {
        if (this.selectedData.length > 0) {
          this.deleteSelected();
        }
      }
      }
    ];
  }

  /**
   * 获取数据库中
   */
  getDataByPage(currentPage: any, rowsPerPage: any, filter: CmUnit) {
    let queryParams = {
      orgName_LIKE: filter.orgName,
      compOrgType_EQ: filter.compOrgType
    };
    if (this.isDropPanelShow) {//高级查询
      queryParams['compOrgStatus_EQ'] = filter.compOrgStatus
    }
    this.httpService
      .findByPage('/cmpsOrgComps/findByCondition', currentPage, rowsPerPage, queryParams)
      .then(res => {
        return this.setData(res);
      });
  }

  /*删除*/
  deleteSelected() {
    this.delete('/cmpsOrgComps/delete', 'id');
  }

  /*重置*/
  reset(): any {
    this.filter = new CmUnit();
    this.search();
  }

}
