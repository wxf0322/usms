import {Component, OnInit, Input, Renderer} from '@angular/core';
import {TreeNode} from "primeng/primeng";
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmationService} from "primeng/components/common/api";
import {SimpleBaseUtil} from "../../../shared/util/simple-base.util";
import {HttpService} from "../../../core/service/http.service";


@Component({
  selector: 'app-role-user-selector',
  templateUrl: './role-user-selector.component.html',
  styleUrls: ['./role-user-selector.component.css']
})
export class RoleUserSelectorComponent extends SimpleBaseUtil<any> implements OnInit {

  selectedNode: TreeNode[];
  /**
   * 用户所选的机构id
   */
  institutionId: string;

  /**
   * 当前组织机构
   */
  institutionName: string;

  @Input() tree: TreeNode[];

  @Input() sourceUsers: any[] = [];

  @Input() targetUsers: any[] = [];


  @Input() gridCode:any;

  @Input() roleId: any;

  //关键字
  key: string = '';


  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
  }

  ngOnInit() {
  }

  nodeSelect(event) {
    this.institutionId = event.node.data.id;
    if (event.node.data.parentId == 0) {
      this.institutionId = null;
    }
    this.institutionName = event.node.data.label;
    this.queryForRole();
  }

  queryForRole() {
    let unSelectedUrl = 'role/users/unselected?key=' + this.key;
    let params = {
      roleId: this.roleId,
      institutionId: this.institutionId
    };
    this.httpService.executeByParams(unSelectedUrl, params).then(
      res => {
        this.sourceUsers = res;
      }
    );
  }


  deleteSelected() {
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
  }
}
