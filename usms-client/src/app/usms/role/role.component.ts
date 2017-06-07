import {Component, OnInit, Renderer} from '@angular/core';
import {Role} from "./role";
import {SimpleBaseUtil} from "../../shared/util/simple-base.util";
import {Router, ActivatedRoute} from "@angular/router";
import {HttpService} from "../../core/service/http.service";
import {ConfirmationService, SelectItem} from "primeng/components/common/api";
import {GlobalVariable} from "../../shared/global-variable";

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css']
})
export class RoleComponent extends SimpleBaseUtil<Role> implements OnInit {

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
    this.selects.push({label: '角色名称', value: 1});
    this.selects.push({label: '角色编码', value: 2});
  }

  ngOnInit(): void {
    this.getDataByPage(0, this.page.size, this.filter);
  }


  /* 分配用户 */
  gotoUserAllocation(id: string) {
    this.router.navigate(['user-allocation', {id: id}], {relativeTo: this.route});
  }

  /* 分配权限 */
  gotoPrivilegeAllocation(id: string) {
    this.router.navigate(['privilege-allocation', {id: id}], {relativeTo: this.route});
  }

  deleteSelected() {
    let url = GlobalVariable.BASE_URL + 'role/delete';
    this.delete(url, 'id');
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: Role) {
    let url = GlobalVariable.BASE_URL + 'role/list';
    this.httpService.findByPage(url, currentPage, rowsPerPage, null).then(
      res => this.setData(res)
    );
  }

  query() {
    var url = GlobalVariable.BASE_URL + 'role/list';
    if (this.selectKey == '1') {
      url = GlobalVariable.BASE_URL + 'role/list?label=' + this.key;
    } else if (this.selectKey == '2') {
      url = GlobalVariable.BASE_URL + 'role/list?name=' + this.key;
    }
    this.httpService.findByPage(url, 0, this.page.size, null).then(
      res => {
        return this.setData(res);
      }
    );
  }
}
