import {OnInit, Component} from "@angular/core";
import {HttpService} from "../../../core/service/http.service";
import {ActivatedRoute} from "@angular/router";
import {Location} from '@angular/common';
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {Privilege} from "../../privilege/privilege";
import {GlobalVariable} from "../../../shared/global-variable";

@Component({
  selector: 'app-privilege-allocation',
  templateUrl: './privilege-allocation.component.html',
  styleUrls: ['./privilege-allocation.component.css']
})

export class PrivilegeAllocationComponent extends SimpleBaseDetailUtil<any> implements OnInit {

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
  }

  sourcePrivileges: Privilege[];

  targetPrivileges: Privilege[];

  goBack() {
    this.location.back();
  }

  save() {
    let id = this.route.snapshot.params['id'];
    let saveUrl = GlobalVariable.BASE_URL + 'role/privileges/update';
    let ids = '';
    for (var i in this.targetPrivileges) {
      ids = ids + this.targetPrivileges[i].id + ',';
    }
    let params = {
      roleId: id,
      privilegeIds: ids
    };
    this.httpService.executeByParams(saveUrl, params).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功更新'
        });
        this.goBack();
      });
  }


  ngOnInit(): void {
    let id = this.route.snapshot.params['id'];
    let selectedUrl = GlobalVariable.BASE_URL + 'role/privileges/selected';
    let unSelectedUrl = GlobalVariable.BASE_URL + 'role/privileges/unselected';
    let params = {
      roleId: id
    };
    this.httpService.executeByParams(unSelectedUrl, params).then(
      res => {this.sourcePrivileges = res;}
    );
    this.httpService.executeByParams(selectedUrl, params).then(
      res => {this.targetPrivileges = res;}
    );

  }

}
