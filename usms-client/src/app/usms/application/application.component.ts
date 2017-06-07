import {Component, OnInit, Renderer} from '@angular/core';
import {SimpleBaseUtil} from '../../shared/util/simple-base.util';
import {Router, ActivatedRoute} from '@angular/router';
import {HttpService} from '../../core/service/http.service';
import {ConfirmationService, SelectItem} from 'primeng/components/common/api';
import {Application} from './application';
import {GlobalVariable} from '../../shared/global-variable';

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.css']
})
export class ApplicationComponent extends SimpleBaseUtil<Application> implements OnInit {
  selects: SelectItem[];
  selectKey: string;
  key: string;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
    this.selects = [];
    this.selects.push({label: '请选择查询条件', value: 0});
    this.selects.push({label: '应用名称', value: 1});
    this.selects.push({label: '应用编码', value: 2});
  }

  deleteSelected() {
    const url = GlobalVariable.BASE_URL + 'application/delete';
    this.delete(url, 'id');
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
    const url = GlobalVariable.BASE_URL + 'application/list';
    this.httpService.findByPage(url, currentPage, rowsPerPage, null).then(
      res => this.setData(res)
    );
  }

  ngOnInit(): void {
    this.getDataByPage(0, this.page.size, this.filter);
  }

  query() {
    var url = GlobalVariable.BASE_URL + 'application/list';
    if (this.selectKey == '1') {
      url = GlobalVariable.BASE_URL + 'application/list?label=' + this.key;
    } else if (this.selectKey == '2') {
      url = GlobalVariable.BASE_URL + 'application/list?name=' + this.key;
    }
    this.httpService.findByPage(url, 0, this.page.size, null).then(
      res => {
        return this.setData(res);
      }
    );
  }
}
