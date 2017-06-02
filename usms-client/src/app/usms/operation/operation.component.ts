import {Component, OnInit, Renderer} from '@angular/core';
import {TreeNode, SelectItem} from 'primeng/primeng';
import {SimpleBaseUtil} from "../../shared/util/simple-base.util";
import {Router, ActivatedRoute} from "@angular/router";
import {HttpService} from "../../core/service/http.service";
import {ConfirmationService} from "primeng/primeng";
import {GlobalVariable} from "../../shared/global-variable";
import {TreeData} from "app/shared/util/tree-data";
import {TreeUtil} from "../../shared/util/tree-util";
import {Operation} from "./operation";

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.css']
})
export class OperationComponent extends SimpleBaseUtil<any> implements OnInit {

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
    this.detailData = new Operation();
  }

  cities: SelectItem[];

  selectedCity: string;

  selectedNode: TreeNode;

  tree: TreeNode[] = [];

  detailData: Operation;

  refreshTree() {
    let url = GlobalVariable.BASE_URL + 'operation/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
      });
  }

  ngOnInit(): void {
    this.refreshTree();
    this.cities = [];
    this.cities.push({label: 'New York', value: {id: 1, name: 'New York'}});
    this.cities.push({label: 'Rome', value: {id: 2, name: 'Rome'}});
  }

  nodeSelect(event) {
    let id = event.node.data.id;
    let url = GlobalVariable.BASE_URL + 'operation/find';
    this.httpService.findById(url, id).then(
      res => {
        this.detailData = res
      }
    );
  }

  /**
   * 新增树形节点
   * @param type
   * @param parentId
   */
  addTreeNode(type: string, parentId: string) {
    this.router.navigate(['detail', {type: type, parentId: parentId}], {relativeTo: this.route});
  }

  /**
   * 删除树形节点
   * @param treeNode
   */
  deleteTreeNode(treeNode: TreeNode) {
    let id = treeNode.data['id'];
    if (treeNode.children.length > 0) {
      this.httpService.setMessage({
        severity: 'error',
        summary: '操作失败',
        detail: '该树形节点存在子节点，不能删除！'
      });
    } else {
      this.confirmationService.confirm({
        message: "你确定要删除该节点的数据？",
        header: "提示",
        accept: () => {
          let url = GlobalVariable.BASE_URL + 'operation/delete';
          let param = {id: treeNode.data.id};
          this.httpService.executeByParams(url, param).then(
            res => {
              this.httpService.setMessage({
                severity: 'success',
                summary: '操作成功',
                detail: '该树形节点删除成功！'
              });
              this.refreshTree();
            }
          );
        }
      });
    }
  }

  deleteSelected() {
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
  }

  reset() {
  }

}
