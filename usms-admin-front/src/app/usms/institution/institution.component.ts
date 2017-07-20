import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TreeNode} from 'primeng/primeng';
import {HttpService} from '../../core/service/http.service';
import {ConfirmationService} from 'primeng/primeng';
import {TreeUtil} from '../../shared/util/tree-util';
import {TreeData} from '../../shared/util/tree-data';
import {InstitutionService} from './institution.service';
import {StringUtil} from "../../shared/util/string-util";

@Component({
  selector: 'app-institution',
  templateUrl: './institution.component.html',
  styleUrls: ['./institution.component.css']
})
export class InstitutionComponent implements OnInit {

  /**
   * 树形数据
   * @type {Array}
   */
  tree: TreeNode[] = [];

  /**
   * 已选中的节点
   */
  selectedNode: TreeNode;

  /**
   * 查询关键字
   */
  queryWord: string;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected institutionService: InstitutionService) {
  }

  /**
   * 初始化操作
   */
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

  /**
   * 刷新树形数据
   */
  refreshTree() {
    const url = 'institution/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
        this.tree[0].expanded = true;
      });
  }

  queryNode() {
    this.selectedNode = TreeUtil.findNodesByLabel(this.tree, StringUtil.trim(this.queryWord));
    const id = this.selectedNode.data.id;
    const parentLabel = '';
    this.router.navigate(['panel',
      {id: id, parentLabel: parentLabel}], {
      relativeTo: this.route,
      skipLocationChange: true
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
    this.router.navigate(['panel',
      {id: id, parentLabel: parentLabel}], {
      relativeTo: this.route,
      skipLocationChange: true
    });
  }

}
