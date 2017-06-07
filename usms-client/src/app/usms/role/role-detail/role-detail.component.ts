import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {HttpService} from "../../../core/service/http.service";
import {Role} from "../role";
import {GlobalVariable} from "../../../shared/global-variable";
import {FormGroup, FormControl, FormBuilder, Validators} from '@angular/forms';
import {CustomValidators} from 'ng2-validation';


@Component({
  selector: 'app-role-detail',
  templateUrl: './role-detail.component.html',
  styleUrls: ['./role-detail.component.css']
})

export class RoleDetailComponent extends SimpleBaseDetailUtil<Role> implements OnInit {

  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Role();
    this.detailData.enabled = 1;
  }

  ngOnInit() {
    let url = GlobalVariable.BASE_URL + 'role/find';
    this.init(url);
  }

  goBack() {
    this.location.back();
  }

  save() {
    let url = GlobalVariable.BASE_URL + 'role/saveOrUpdate';
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


}
