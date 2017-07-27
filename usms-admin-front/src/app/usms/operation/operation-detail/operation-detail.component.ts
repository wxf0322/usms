import {Component, OnInit, ViewChild} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Location} from '@angular/common';
import {NgForm} from "@angular/forms";
import {BaseDetail} from "../../../shared/base-component/base-detail";
import {HttpService} from "../../../core/service/http.service";
import {Operation} from "../operation";
import {OperationService} from "../operation.service";

@Component({
  selector: "app-operation-detail",
  templateUrl: "./operation-detail.component.html",
  styleUrls: ["./operation-detail.component.css"]
})
export class OperationDetailComponent extends BaseDetail<Operation> implements OnInit {

  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute,
              protected operationService: OperationService) {
    super(httpService, route);
    this.detailData = new Operation();
    this.detailData.optType = 1;
    this.detailData.enabled = 1;
  }

  ngOnInit(): void {
    const url = 'operation/find';
    this.init(url);
  }

  goBack() {
    this.location.back();
  }

  save() {
    let url = 'operation/saveOrUpdate';
    if (this.detailData.id == null) {
      this.detailData.parentId = this.route.snapshot.params['parentId'];
    }
    if (this.detailData.applicationId == null) {
      this.detailData.applicationId = +this.route.snapshot.params['applicationId'];
    }
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          detail: '操作成功'
        });
        this.operationService.sendMessage('refresh');
        this.goBack();
      });
  }

}
