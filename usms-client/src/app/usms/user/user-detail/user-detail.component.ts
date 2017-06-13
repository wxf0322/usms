import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Location} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {SimpleBaseDetailUtil} from '../../../shared/util/simple-base-detail.util';
import {HttpService} from '../../../core/service/http.service';
import {GlobalVariable} from '../../../shared/global-variable';
import {User} from '../user';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent extends SimpleBaseDetailUtil<User> implements OnInit {

  // 表单验证
  @ViewChild('reForm') reForm: NgForm;

  roleDisplay = true;

  date: Date;

  sourceRoles: any[];

  targetRoles: any[];

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new User();
    this.detailData.enabled = 1;
    this.detailData.sex = 1;
  }

  ngOnInit(): void {
    const url = GlobalVariable.BASE_URL + 'user/find';
    this.init(url).then(
      res => {
        if (this.detailData.birthday != null) {
          this.date = new Date(this.detailData.birthday);
        }else {
          this.date = new Date();
        }
      });
  }

  goBack() {
    this.location.back();
  }

  save() {
    this.detailData.birthday = this.date.getTime();
    let institutionId = this.route.snapshot.params['institutionId'];
    if (typeof (institutionId) === 'undefined') {
      institutionId = '';
    }
    const url = GlobalVariable.BASE_URL + 'user/saveOrUpdate?institutionId=' + institutionId;
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
