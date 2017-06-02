import {Component, OnInit, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {HttpService} from "../../../../core/service/http.service";
import {CmCenter} from "../cm-center.entity";
import {SimpleBaseDetailUtil} from "../../../../shared/util/simple-base-detail.util";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-cm-center-detail',
  templateUrl: './cm-center-detail.component.html',
  styleUrls: ['./cm-center-detail.component.css'],
})
export class CmCenterDetailComponent extends SimpleBaseDetailUtil<CmCenter> implements OnInit {

  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);

  }

  ngOnInit() {
    this.detailData = new CmCenter();
    /*初始化，获得操作类型和数据*/
    this.init('/cmpsOrgCompCenters/getById');

  }

  //保存
  save() {
    this.detailData.creatorId = '110';
    this.detailData.modifierId = '110';
    let url = '';
    if (this.operateType === 'add') {
      url = '/cmpsOrgCompCenters/save';
    }
    if (this.operateType === 'edit') {
      url = '/cmpsOrgCompCenters/update';
    }
    this.httpService
      .saveOrUpdate(url, this.detailData)
      .then(res => {
        //消息提示
        this.httpService.setMessage(
          {
            severity: 'success',
            summary: '操作成功',
            detail: '成功更新' + this.detailData.centerName
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
