import {Component, OnInit,Renderer} from '@angular/core';
import {ConfirmationService, MenuItem, SelectItem} from "primeng/primeng";
import {ActivatedRoute, Router} from "@angular/router";
import 'rxjs/add/operator/switchMap';
import {SimpleBaseUtil} from "../../../shared/util/simple-base.util";
import {HttpService} from "../../../core/service/http.service";
import {MpmtUnit} from "./mpmt-unit.entity";

@Component({
  selector: 'app-mpmt-unit',
  templateUrl: './mpmt-unit.component.html',
  styleUrls: ['./mpmt-unit.component.css']
})
export class MpmtUnitComponent extends SimpleBaseUtil<MpmtUnit> implements OnInit {
  orgTypes: SelectItem[]; // 机构类型
  items: MenuItem[];//右键操作
  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router,route,httpService, confirmationService,renderer);

  }

  ngOnInit() {
    this.initBtn();

    this.orgTypes = [];
    this.orgTypes.push({label: '- 组织类型 -', value: null});
    this.orgTypes.push({label: '1', value: 1});
    this.orgTypes.push({label: '2', value: 2});
    this.orgTypes.push({label: '3', value: 3});
    this.orgTypes.push({label: '4', value: 4});

    //数据初始化
    this.filter = new MpmtUnit();
    this.getDataByPage(0, this.page.size, this.filter);
  }


  /*按钮初始化*/
  initBtn(){

    //右击操作初始化
    this.items = [
      {
        label: '编辑', icon: 'fa-edit', command: (event) => {
        if (this.selectedData.length == 1) {
          this.gotoDetail('edit', this.selectedData[0].id)
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
  getDataByPage(currentPage: any, rowsPerPage: any, filter: MpmtUnit) {
    let queryParams = {
      massesName_LIKE: filter.orgName
    };
    if (this.isDropPanelShow) {//高级查询
      queryParams['compMassOrgStatus_EQ'] = filter.compMassOrgStatus
    }
    this.httpService
      .findByPage('/cmpsOrgCompMasses/findByCondition', currentPage, rowsPerPage, queryParams)
      .then(res => {
        return this.setData(res);
      });
  }



  /*删除*/
  deleteSelected() {
    this.delete('/cmpsOrgCompMasses/delete', 'id');
  }


  /*重置*/
  reset(): any {
    this.filter = new MpmtUnit();
    this.getDataByPage(0, this.page.size, this.filter);
    this.isDropPanelShow = false;
  }

}
