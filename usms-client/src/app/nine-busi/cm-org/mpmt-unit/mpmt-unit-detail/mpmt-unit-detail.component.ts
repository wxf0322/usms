import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {HttpService} from "../../../../core/service/http.service";
import {MpmtUnit} from "../mpmt-unit.entity";

@Component({
  selector: 'app-mpmt-unit-detail',
  templateUrl: './mpmt-unit-detail.component.html',
  styleUrls: ['./mpmt-unit-detail.component.css'],
})
export class MpmtUnitDetailComponent implements OnInit {

  operateType: string;//操作类型
  detailData: MpmtUnit;//详细数据

  constructor(private location: Location,
              private httpService: HttpService,
              protected route: ActivatedRoute) {

  }

  ngOnInit() {
    this.detailData = new MpmtUnit();
    this.init();
  }

  //初始化
  init() {
    this.operateType = this.route.snapshot.params['type'];
    //不是新增操作，获取详细数据
    if (this.operateType !== 'add') {
      let dataId=this.route.snapshot.params['id'];
      this.httpService
        .findById('/cmpsOrgCompMasses/getById',dataId)
        .then(res=>{
          this.detailData=res;
        });
    }
  }

  // 返回
  goBack(): void {
    this.location.back();
  }

  //保存
  save() {
    this.detailData.creatorId='110';
    this.detailData.modifierId='110';
    let url='';
    //CMPS_ORG_COMP_MASSES
    if(this.operateType==='add'){
      url='/cmpsOrgCompMasses/save';
    }
    if(this.operateType==='edit'){
      url='/cmpsOrgCompMasses/update';
    }
    this.httpService
      .saveOrUpdate(url, this.detailData)
      .then(res => {
        //消息提示
        this.httpService.setMessage(
          {
            severity: 'success',
            summary: '操作成功',
            detail: '成功更新'+this.detailData.orgName
          }
        );
        this.goBack();
      })
  }
}
