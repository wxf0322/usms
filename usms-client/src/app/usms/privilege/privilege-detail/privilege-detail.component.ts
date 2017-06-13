import {Component, OnInit,ViewChild} from '@angular/core';
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {NgForm} from "@angular/forms";
import {Location} from '@angular/common';
import {HttpService} from "../../../core/service/http.service";
import {ActivatedRoute} from "@angular/router";
import {GlobalVariable} from "../../../shared/global-variable";
import {Privilege} from "../privilege";

@Component({
  selector: 'app-privilege-detail',
  templateUrl: './privilege-detail.component.html',
  styleUrls: ['./privilege-detail.component.css']
})
export class PrivilegeDetailComponent extends SimpleBaseDetailUtil<Privilege> implements OnInit {
  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService,route);
    this.detailData = new Privilege();
    this.detailData.enabled=1;
  }

  goBack() {
    this.location.back();
  }

  save() {
    let url = GlobalVariable.BASE_URL + 'privilege/saveOrUpdate';
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功更新' + this.detailData.name
        });
        this.goBack();
      });
  }

  ngOnInit(): void {
    let url = GlobalVariable.BASE_URL + 'privilege/find';
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

