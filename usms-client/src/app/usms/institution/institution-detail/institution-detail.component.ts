import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {HttpService} from "../../../core/service/http.service";
import {GlobalVariable} from "../../../shared/global-variable";
import {Institution} from "../institution";

@Component({
  selector: 'institution-detail',
  templateUrl: './institution-detail.component.html',
  styleUrls: ['./institution-detail.component.css']
})
export class InstitutionDetailComponent extends SimpleBaseDetailUtil<Institution>
  implements OnInit {

  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Institution();
    this.detailData.enabled=1;
    this.detailData.type=1;
  }

  ngOnInit(): void {
    let url = GlobalVariable.BASE_URL + 'institution/find';
    this.init(url);
  }

  goBack() {
    this.location.back();
  }

  save() {
    let url =  GlobalVariable.BASE_URL + 'institution/saveOrUpdate';
    if(this.detailData.id == null) {
      this.detailData.parentId = this.route.snapshot.params['parentId'];
    }
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '树形数据插入成功' + this.detailData.label
        });
        this.goBack();
      });
  }

}
