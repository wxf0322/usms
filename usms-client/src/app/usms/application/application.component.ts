import {Component, OnInit, ViewChild, Renderer} from '@angular/core';
import {BaseTable} from '../../shared/util/base-table';
import {Router, ActivatedRoute} from '@angular/router';
import {HttpService} from '../../core/service/http.service';
import {ConfirmationService} from 'primeng/components/common/api';
import {Application} from './application';
import {ApplicationDialogComponent} from './application-dialog/application-dialog.component';
import {StringUtil} from "../../shared/util/string-util";

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.css']
})
export class ApplicationComponent extends BaseTable<Application> implements OnInit {

  @ViewChild(ApplicationDialogComponent)
  applicationDialog: ApplicationDialogComponent;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
  }

  deleteSelected() {
    const url = 'application/delete';
    this.delete(url, 'id');
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
    const url = 'application/list?key=' + StringUtil.trim(this.filter);
    this.httpService.findByPage(url, currentPage, rowsPerPage, this.filter).then(
      res => this.setData(res)
    );
  }

  showDialog(type: string, id: string) {
    this.applicationDialog.showDialog(type, id);
  }

  ngOnInit(): void {
    this.filter = '';
    this.getDataByPage(0, this.page.size, this.filter);
  }

  query() {
    this.getDataByPage(0, this.page.size, this.filter);
  }

  onSaved(event) {
    this.getDataByPage(this.page.number, this.page.size, this.filter);
  }

}
