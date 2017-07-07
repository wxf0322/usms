import {Component, OnInit, Renderer, ViewChild} from '@angular/core';
import {Role} from "./role";
import {BaseTable} from "../../shared/util/base-table";
import {Router, ActivatedRoute} from "@angular/router";
import {HttpService} from "../../core/service/http.service";
import {ConfirmationService} from "primeng/components/common/api";
import {RoleDialogComponent} from "./role-dialog/role-dialog.component";
import {StringUtil} from "../../shared/util/string-util";


@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css']
})
export class RoleComponent extends BaseTable<Role> implements OnInit {

  @ViewChild(RoleDialogComponent)
  roleDialog: RoleDialogComponent;


  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
  }

  dialogDisplay: boolean = false;
  roleId: any = null;

  ngOnInit(): void {
    this.filter = '';
    this.getDataByPage(0, this.page.size, this.filter);
  }

  deleteSelected() {
    let url = 'role/delete';
    this.delete(url, 'id');
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: Role) {
    const url = 'role/list?key=' + StringUtil.trim(this.filter);
    this.httpService.findByPage(url, currentPage, rowsPerPage, this.filter).then(
      res => this.setData(res)
    );
  }

  query() {
    this.getDataByPage(0, this.page.size, this.filter);
  }

  showDialog(type: string, roleId: string) {
    this.roleId = roleId;
    this.roleDialog.showDialog(type, roleId);
  }

  onSaved(event) {
    this.getDataByPage(this.page.number, this.page.size, this.filter);
  }

}
