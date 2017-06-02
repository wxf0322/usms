import {Component, OnInit, Renderer} from '@angular/core';
import {ConfirmationService, MenuItem, SelectItem, TreeNode} from "primeng/primeng";
import {ActivatedRoute, Router} from "@angular/router";
import 'rxjs/add/operator/switchMap';
import {HttpService} from "../../../core/service/http.service";
import {NetInfoCenter} from "./net-info-center.entity";
import {SimpleBaseUtil} from "../../../shared/util/simple-base.util";

@Component({
  selector: 'app-net-info-center',
  templateUrl: './net-info-center.component.html',
  styleUrls: ['./net-info-center.component.css']
})
export class NetInfoCenterComponent extends SimpleBaseUtil<NetInfoCenter> implements OnInit {


  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer:Renderer) {
    super(router, route, httpService, confirmationService,renderer);
  }

  ngOnInit() {
    this.initBtn();

    //数据初始化
    this.filter = new NetInfoCenter();
    this.getDataByPage(0, this.page.size, this.filter);

  }

  /*功能按钮初始化*/
  initBtn() {
    //右击操作
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
  getDataByPage(currentPage: any, rowsPerPage: any, filter: NetInfoCenter) {
    let queryParams = {
      centerName_LIKE: filter.centerName,
      gridId_EQ:filter.gridId
    };
    if (this.isDropPanelShow) {//高级查询
      queryParams['centerStatus_EQ'] = filter.centerStatus;
      queryParams['is24HOnduty_EQ'] = filter.is24HOnduty;
    }
    console.log('query',queryParams);
    this.httpService
      .findByPage('/cmpsOrgVideoCenters/findByCondition', currentPage, rowsPerPage, queryParams)
      .then(res => {
        return this.setData(res);
      });
  }


  /*删除*/
  deleteSelected() {
    this.delete('/cmpsOrgVideoCenters/delete', 'id');
  }

  /*重置*/
  reset(): any {
    this.filter = new NetInfoCenter();
    this.search();
  }

  //选择所属网格
  getSelectedData(event){
    this.filter.gridId=event[0].gridId;
  }
}
