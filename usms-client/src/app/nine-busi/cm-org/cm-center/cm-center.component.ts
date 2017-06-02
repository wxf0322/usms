import {Component, OnInit, Renderer} from '@angular/core';
import {ConfirmationService, MenuItem, SelectItem} from "primeng/primeng";
import {ActivatedRoute, Router} from "@angular/router";
import 'rxjs/add/operator/switchMap';
import {HttpService} from "../../../core/service/http.service";
import {CmCenter} from "./cm-center.entity";
import {SimpleBaseUtil} from "../../../shared/util/simple-base.util";

@Component({
  selector: 'app-cm-center',
  templateUrl: './cm-center.component.html',
  styleUrls: ['./cm-center.component.css']
})
export class CmCenterComponent extends SimpleBaseUtil<CmCenter> implements OnInit {

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router,route,httpService, confirmationService,renderer);

  }

  ngOnInit() {
    this.initBtn();
    //数据初始化
    this.filter = new CmCenter();
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
  getDataByPage(currentPage: any, rowsPerPage: any, filter: CmCenter) {
    let queryParams = {
      centerName_LIKE: filter.centerName
    };
    if (this.isDropPanelShow) {//高级查询
      queryParams['centerStatus_EQ'] = filter.centerStatus
    }
    this.httpService
      .findByPage('/cmpsOrgCompCenters/findByCondition', currentPage, rowsPerPage, queryParams)
      .then(res => {
        return this.setData(res);
      });
  }

  /*删除*/
  deleteSelected() {
    this.delete('/cmpsOrgCompCenters/delete', 'id');
  }


  /*重置*/
  reset(): any {
    this.filter = new CmCenter();
    this.search();
  }


}
