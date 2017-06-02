import {Component, OnInit, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {SeriousCase} from "../serious-case.entity";
import {HttpService} from "../../../../core/service/http.service";
import {SimpleBaseDetailUtil} from "../../../../shared/util/simple-base-detail.util";
import {NgForm} from "@angular/forms";
import {SelectItem} from "primeng/primeng";


@Component({
  selector: 'app-serious-case-detail',
  templateUrl: './serious-case-detail.component.html',
  styleUrls: ['./serious-case-detail.component.css']
})
export class SeriousCaseDetailComponent extends SimpleBaseDetailUtil<SeriousCase> implements OnInit {

  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  caseLvlCdes: SelectItem[] = [
    {label: '等级一', value: '1'},
    {label: '等级二', value: '2'},
  ]; // 案件等级

  caseTypes: SelectItem[] = [
    {label: '类型一', value: '1'},
    {label: '类型二', value: '2'},
  ]; //事件类型

  seriousCaseStatuses: SelectItem[] = [
    {label: '状态一', value: '1'},
    {label: '状态二', value: '2'},
  ]; // 事件状态

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
  }

  ngOnInit() {
    this.detailData = new SeriousCase();
    this.init('/cmpsEvtSeriousCases/getById')
      .then(res=>{
        if(res){ //如果返回true,有日期字段要记得转换
          this.timestampToDate(this.detailData);
        }
      });
  }

  /*日期格式转为时间戳*/
  dateToTimestamp(temp: SeriousCase) {
    if (temp.occurDate != null) temp.occurDate = temp.occurDate.getTime();
  }

  /*日期格式转为Date*/
  timestampToDate(temp: SeriousCase) {
    if (temp.occurDate != null) temp.occurDate = new Date(temp.occurDate);
  }


  //保存
  save() {
    this.detailData.creatorId = '110';
    this.detailData.modifierId = '110';
    //日期格式转换
    this.dateToTimestamp(this.detailData);

    let url = '';
    if (this.operateType === 'add') {
      url = '/cmpsEvtSeriousCases/save';
    }
    if (this.operateType === 'edit') {
      url = '/cmpsEvtSeriousCases/update';
    }
    this.httpService
      .saveOrUpdate(url, this.detailData)
      .then(res => {
        //消息提示
        this.httpService.setMessage(
          {
            severity: 'success',
            summary: '操作成功',
            detail: '成功更新' + this.detailData.caseName
          }
        );
        this.goBack();
      })
  }

  // 返回
  goBack(): void {
    this.location.back();
  }

}
