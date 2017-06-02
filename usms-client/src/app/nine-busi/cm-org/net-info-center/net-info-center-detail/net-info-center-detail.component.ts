import {Component, OnInit, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {HttpService} from "../../../../core/service/http.service";
import {SimpleBaseDetailUtil} from "../../../../shared/util/simple-base-detail.util";
import {NgForm} from "@angular/forms";
import {NetInfoCenter} from "../net-info-center.entity";


@Component({
  selector: 'app-net-info-center-detail',
  templateUrl: './net-info-center-detail.component.html',
  styleUrls: ['./net-info-center-detail.component.css']
})
export class NetInfoCenterDetailComponent extends SimpleBaseDetailUtil<NetInfoCenter> implements OnInit {

  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
  }

  ngOnInit() {
    this.detailData = new NetInfoCenter();
    this.init('/cmpsOrgVideoCenters/getById')
      .then(res=>{
        if(!res){
          this.detailData.is24HOnduty='1';
        }
      });

  }


  //保存
  save() {
    this.detailData.creatorId = '110';
    this.detailData.modifierId = '110';
    let url = '';
    if (this.operateType === 'add') {
      url = '/cmpsOrgVideoCenters/save';
    }
    if (this.operateType === 'edit') {
      url = '/cmpsOrgVideoCenters/update';
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
