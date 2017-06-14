/**
 * Created by Arno Chen on 2017/5/17.
 */
import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {HttpService} from "../../../core/service/http.service";
import {Application} from "../application";

@Component({
  selector: 'app-privilege-detail',
  templateUrl: './application-detail.component.html',
  styleUrls: ['./application-detail.component.css']
})
export class ApplicationDetailComponent extends SimpleBaseDetailUtil<Application> implements OnInit {
  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Application();
    this.detailData.enabled=1;
  }
  goBack() {
    this.location.back();
  }

  save() {
    let url;
    if (this.detailData.id == null) {
      url = 'application/create';
    } else {
      url = 'application/update';
    }
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功' + this.detailData.name
        });
        this.goBack();
      });
  }

  ngOnInit(): void {
    let url = 'application/find';
    this.init(url);
  }

  //取消或保存
  doExecute(event) {
    if (event.goBack) {
      this.goBack();
    }
    if (event.save) {
      this.save();
    }
  }

}

