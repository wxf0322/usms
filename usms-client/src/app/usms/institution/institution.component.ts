import {Component, OnInit, Renderer} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TreeNode} from 'primeng/primeng';
import {HttpService} from '../../core/service/http.service';
import {ConfirmationService} from 'primeng/primeng';
import {SimpleBaseUtil} from '../../shared/util/simple-base.util';
import {TreeUtil} from '../../shared/util/tree-util';
import {TreeData} from '../../shared/util/tree-data';
import {InstitutionService} from "./institution.service";

@Component({
  selector: 'app-institution',
  templateUrl: './institution.component.html',
  styleUrls: ['./institution.component.css']
})
export class InstitutionComponent extends SimpleBaseUtil<any> implements OnInit {

  tree: TreeNode[] = [];

  selectedNode: TreeNode;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer,
              protected institutionService: InstitutionService) {
    super(router, route, httpService, confirmationService, renderer);
  }

  ngOnInit(): void {
    this.refreshTree();
    this.institutionService.message$.subscribe(
      msg => {
        this.refreshTree();
      }, error => {
        console.error('error: ' + error);
      }
    );
  }

  refreshTree() {
    const url = 'institution/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
      });
  }

  /**
   * 单击树形节点
   * @param event
   */
  nodeSelect(event) {
    const id = event.node.data.id;
    let parentLabel;
    if (event.node.parent == null) {
      parentLabel = '';
    } else {
      parentLabel = event.node.parent.label;
    }
    this.router.navigate(['panel', {id: id, parentLabel: parentLabel}], {
      relativeTo: this.route,
      skipLocationChange: true
    });
  }

  deleteSelected() {
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
  }
}
