import {Component, OnInit, ViewChild} from "@angular/core";
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {NgForm} from "@angular/forms";
import {HttpService} from "../../../core/service/http.service";
import {ActivatedRoute} from "@angular/router";
import {Location} from '@angular/common';
import {Operation} from "../operation";
import {GlobalVariable} from "../../../shared/global-variable";

@Component({
  selector: "app-operation-detail",
  templateUrl: "./operation-detail.component.html",
  styleUrls: ["./operation-detail.component.css"]
})
export class OperationDetailComponent extends SimpleBaseDetailUtil<Operation>
  implements OnInit {

  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Operation();
    this.detailData.enabled = 1;
  }

  ngOnInit(): void {
    let url = GlobalVariable.BASE_URL + 'operation/find';
    this.init(url);
  }

  goBack() {
    this.location.back();
  }

  save() {
    let url =  GlobalVariable.BASE_URL + 'operation/saveOrUpdate';
    if(this.detailData.id == null) {
      this.detailData.parentId = this.route.snapshot.params['parentId'];
    }
    this.detailData.applicationId = +this.route.snapshot.params['applicationId'];
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
