import {Component, OnInit, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {CmUnit} from "../cm-unit";
import {ActivatedRoute} from "@angular/router";
import {HttpService} from "../../../../core/service/http.service";
import {SimpleBaseDetailUtil} from "../../../../shared/util/simple-base-detail.util";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-cm-unit-detail',
  templateUrl: './cm-unit-detail.component.html',
  styleUrls: ['./cm-unit-detail.component.css']
})
export class CmUnitDetailComponent extends SimpleBaseDetailUtil<CmUnit> implements OnInit {

  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService,route);
  }

  ngOnInit() {
    this.detailData = new CmUnit();
    this.init('');
  }

  //保存
  save() {
    let url = '';
    if (this.operateType === 'add') {
      url = '/cmpsOrgComps/save';
    }
    if (this.operateType === 'edit') {
      url = '/cmpsOrgComps/update';
    }
    this.httpService
      .saveOrUpdate(url, this.detailData)
      .then(res => {
        //消息提示
        this.httpService.setMessage(
          {
            severity: 'success',
            summary: '操作成功',
            detail: '成功更新' + this.detailData.orgName
          }
        );
        this.goBack();
      });
  }

  // 返回
  goBack(): void {
    this.location.back();
  }

}
