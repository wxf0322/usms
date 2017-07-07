import {Component, OnInit, Input, Renderer} from '@angular/core';
import {TreeNode} from 'primeng/primeng';
import {ActivatedRoute, Router} from '@angular/router';
import {ConfirmationService} from 'primeng/components/common/api';
import {HttpService} from '../../../core/service/http.service';
import {TreeUtil} from "../../../shared/util/tree-util";
import {StringUtil} from "../../../shared/util/string-util";

@Component({
  selector: 'app-role-user-selector',
  templateUrl: './role-user-selector.component.html',
  styleUrls: ['./role-user-selector.component.css']
})
export class RoleUserSelectorComponent implements OnInit {

  selectedNode: TreeNode;
  /**
   * 用户所选的机构id
   */
  institutionId: string;

  /**
   * 当前组织机构
   */
  institutionName: string;

  @Input() tree: TreeNode[] ;

  @Input() sourceUsers: any[] = [];

  @Input() targetUsers: any[] = [];

  @Input() roleId: any;

  // 关键字
  key = '';

  // 组织机构关键字
  queryWord = '';

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
  }

  ngOnInit() {
  }

  nodeSelect(event) {
    this.institutionId = event.node.data.id;
    if (event.node.data.parentId === 0) {
      this.institutionId = null;
    }
    this.institutionName = event.node.data.label;
    this.queryForRole();
  }

  queryForRole() {
    const sourceUrl = 'role/users/source?key=' + StringUtil.trim(this.key);
    const params = {
      roleId: this.roleId,
      institutionId: this.institutionId
    };
    this.httpService.findByParams(sourceUrl, params).then(
      res => this.sourceUsers = res
    );
  }

  queryNode() {
    this.key = '';
    this.selectedNode = TreeUtil.findNodesByLabel(this.tree, StringUtil.trim(this.queryWord));
    this.institutionId = this.selectedNode.data.id;
    if (this.selectedNode.data.parentId === 0) {
      this.institutionId = null;
    }
    this.institutionName = this.selectedNode.data.label;
    this.queryForRole();
  }

}
