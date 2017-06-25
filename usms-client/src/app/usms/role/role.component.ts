import {Component, OnInit, Renderer} from '@angular/core';
import {Role} from "./role";
import {BaseTable} from "../../shared/util/base-table";
import {Router, ActivatedRoute} from "@angular/router";
import {HttpService} from "../../core/service/http.service";
import {ConfirmationService} from "primeng/components/common/api";

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css']
})
export class RoleComponent extends BaseTable<Role> implements OnInit {

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
    const url = 'role/delete';
    this.delete(url, 'id');
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: Role) {
    const url = 'role/list?key=' + this.filter;
    this.httpService.findByPage(url, currentPage, rowsPerPage, this.filter).then(
      res => this.setData(res)
    );
  }

  query() {
    const url = 'role/list?key=' + this.filter;
    this.httpService.findByPage(url, 0, this.page.size, this.filter).then(
      res => this.setData(res)
    );
  }

}
