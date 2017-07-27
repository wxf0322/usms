import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Location} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {BaseDetail} from '../../../shared/base-component/base-detail';
import {HttpService} from '../../../core/service/http.service';
import {Institution} from '../institution';
import {InstitutionService} from '../institution.service';
import {isNullOrUndefined} from "util";

@Component({
  selector: 'institution-detail',
  templateUrl: './institution-detail.component.html',
  styleUrls: ['./institution-detail.component.css']
})
export class InstitutionDetailComponent extends BaseDetail<Institution>
  implements OnInit {

  /**
   * 表单验证
   */
  @ViewChild('reForm') reForm: NgForm;

  /**
   * 父节点标签名称
   */
  parentLabel: string;

  /**
   * 手动排序的隐藏
   */
  manualSnDisplay: boolean;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute,
              protected institutionService: InstitutionService) {
    super(httpService, route);
    this.detailData = new Institution();
  }

  /**
   * 初始化操作
   */
  ngOnInit(): void {
    this.detailData.enabled = 1;
    this.detailData.type = 1;
    this.parentLabel = this.route.snapshot.params['parentLabel'];
    const url = 'institution/find';
    this.manualSnDisplay = !(this.route.snapshot.params['type'] === 'add');
    this.init(url).then(res => {
      //解决页面上手动排序字段位数显示问题
      if (this.route.snapshot.params['type'] === 'edit') {
        if (isNullOrUndefined(this.detailData.manualSn.length)) {
          this.detailData.manualSn = this.detailData.manualSn.toString();
        }
      }
    });
  }

  /**
   * 返回上级操作
   */
  goBack() {
    this.location.back();
  }

  /**
   * 保存操作
   */
  save() {
    const url = 'institution/saveOrUpdate';
    if (this.detailData.id == null) {
      this.detailData.parentId = this.route.snapshot.params['parentId'];
    }
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          detail: '操作成功',
        });
        this.institutionService.sendMessage('refreshTree');
        this.goBack();
      });
  }

}
