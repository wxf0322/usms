import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Location} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {SimpleBaseDetailUtil} from '../../../shared/util/simple-base-detail.util';
import {HttpService} from '../../../core/service/http.service';
import {GlobalVariable} from '../../../shared/global-variable';
import {Institution} from '../institution';
import {InstitutionService} from '../institution.service';

@Component({
  selector: 'institution-detail',
  templateUrl: './institution-detail.component.html',
  styleUrls: ['./institution-detail.component.css']
})
export class InstitutionDetailComponent extends SimpleBaseDetailUtil<Institution>
  implements OnInit {

  // 表单验证
  @ViewChild('reForm') reForm: NgForm;

  parentLabel: string;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute,
              protected institutionService: InstitutionService) {
    super(httpService, route);
    this.detailData = new Institution();
    this.detailData.enabled = 1;
    this.detailData.type = 1;
  }

  /**
   * 初始化操作
   */
  ngOnInit(): void {
    this.parentLabel = this.route.snapshot.params['parentLabel'];
    const url = GlobalVariable.BASE_URL + 'institution/find';
    this.init(url);
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
    const url = GlobalVariable.BASE_URL + 'institution/saveOrUpdate';
    if (this.detailData.id == null) {
      this.detailData.parentId = this.route.snapshot.params['parentId'];
    }
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '组织机构数据保存成功' + this.detailData.label
        });
        this.institutionService.sendMessage('refreshTree');
        this.goBack();
      });
  }

}
