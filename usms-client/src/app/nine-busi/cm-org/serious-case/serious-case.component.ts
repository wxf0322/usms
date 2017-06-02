import {Component, OnInit, Renderer} from '@angular/core';
import {ConfirmationService, MenuItem, SelectItem} from "primeng/primeng";
import {ActivatedRoute, Router} from "@angular/router";
import 'rxjs/add/operator/switchMap';
import {HttpService} from "../../../core/service/http.service";
import {SeriousCase} from "./serious-case.entity";
import {SimpleBaseUtil} from "../../../shared/util/simple-base.util";

@Component({
  selector: 'app-serious-case',
  templateUrl: './serious-case.component.html',
  styleUrls: ['./serious-case.component.css']
})
export class SeriousCaseComponent extends SimpleBaseUtil<SeriousCase> implements OnInit {


  caseLvlCdes: SelectItem[] = [
    {label: '等级一', value: '1'},
    {label: '等级二', value: '2'},
  ]; // 案件等级

  selectedCaseLvlCdes:any;//高级查询选中的案件等级

  caseTypes: SelectItem[] = [
    {label: '类型一', value: '1'},
    {label: '类型二', value: '2'},
  ]; //事件类型

  selectedCaseTypes:any;//高级查询选中的事件类型

  seriousCaseStatuses: SelectItem[] = [
    {label: '状态一', value: '1'},
    {label: '状态二', value: '2'},
  ]; // 事件状态

  selectedSeriousCaseStatuses:any;//高级查询选中的事件状态

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer:Renderer) {
    super(router, route, httpService, confirmationService,renderer) ;
  }

  ngOnInit() {
    this.initBtn();

    //数据初始化
    this.filter = new SeriousCase();
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
  getDataByPage(currentPage: any, rowsPerPage: any, filter: SeriousCase) {
    let queryParams = {
      caseName_LIKE: filter.caseName
    };
    if (this.isDropPanelShow) {//高级查询
      queryParams['caseLvlCde_EQ'] = this.queryParamsToString(this.selectedCaseLvlCdes);
      queryParams['caseType_EQ'] = this.queryParamsToString(this.selectedCaseTypes);
      queryParams['seriousCaseStatus_EQ'] = this.queryParamsToString(this.selectedSeriousCaseStatuses);
    }
    this.httpService
      .findByPage('/cmpsEvtSeriousCases/findByCondition', currentPage, rowsPerPage, queryParams)
      .then(res => {
        return this.setData(res);
      });
  }


  /*删除*/
  deleteSelected() {
    this.delete('/cmpsEvtSeriousCases/delete', 'id');
  }

  /*重置*/
  reset(): any {
    this.filter = new SeriousCase();
    this.selectedCaseTypes=[];
    this.selectedCaseLvlCdes=[];
    this.selectedSeriousCaseStatuses=[];
    this.search();
  }

}
