import {Component, OnInit, Renderer, ViewChild} from '@angular/core';
import {BaseTable} from '../../shared/base-component/base-table';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../../core/service/http.service';
import {ConfirmationService} from 'primeng/primeng';
import {Privilege} from './privilege';
import {PrivilegeDialogComponent} from './privilege-dialog/privilege-dialog.component';
import {StringUtil} from "../../shared/util/string-util";

@Component({
  selector: 'app-privilege',
  templateUrl: './privilege.component.html',
  styleUrls: ['./privilege.component.css']
})
export class PrivilegeComponent extends BaseTable<Privilege> implements OnInit {

  @ViewChild(PrivilegeDialogComponent)
  privilegeDialog: PrivilegeDialogComponent;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
  }

  ngOnInit(): void {
    this.filter = '';
    this.getDataByPage(0, this.page.size, this.filter);
  }

  deleteSelected() {
    const url = 'privilege/delete';
    this.delete(url, 'id');
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
    const url = 'privilege/list?key=' + StringUtil.trim(this.filter);
    this.httpService.findByPage(url, currentPage, rowsPerPage, null).then(
      res => this.setData(res)
    );
  }

  query() {
    this.getDataByPage(0, this.page.size, this.filter);
  }

  showDialog(type: string, id: string) {
    this.privilegeDialog.showDialog(type, id);
  }

  onSaved(event) {
    this.getDataByPage(this.page.number, this.page.size, this.filter);
  }

}
