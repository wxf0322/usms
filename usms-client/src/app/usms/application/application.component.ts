import {Component, OnInit, Renderer} from '@angular/core';
import {SimpleBaseUtil} from '../../shared/util/simple-base.util';
import {Router, ActivatedRoute} from '@angular/router';
import {HttpService} from '../../core/service/http.service';
import {ConfirmationService} from 'primeng/components/common/api';
import {Application} from './application';
import {GlobalVariable} from '../../shared/global-variable';

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.css']
})
export class ApplicationComponent extends SimpleBaseUtil<Application> implements OnInit {
  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
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

  reset() {
  }

  ngOnInit(): void {
    this.getDataByPage(0, this.page.size, this.filter);
  }

}
