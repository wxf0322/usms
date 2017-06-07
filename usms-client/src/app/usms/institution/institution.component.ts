import {Component, OnInit, Renderer} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TreeNode} from 'primeng/primeng';
import {HttpService} from "../../core/service/http.service";
import {ConfirmationService, MenuItem, SelectItem} from "primeng/primeng";
import {SimpleBaseUtil} from "../../shared/util/simple-base.util";
import {TreeUtil} from "../../shared/util/tree-util";
import {TreeData} from "../../shared/util/tree-data";
import {GlobalVariable} from "../../shared/global-variable";
import {Institution} from "./institution";

@Component({
  selector: 'app-institution',
  templateUrl: './institution.component.html',
  styleUrls: ['./institution.component.css']
})
export class InstitutionComponent extends SimpleBaseUtil<any> implements OnInit {


  tree: TreeNode[] = [];

  selectedNode: TreeNode;

  detailData: Institution;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
    this.detailData = new Institution();
  }

  ngOnInit(): void {
    this.refreshTree();
  }

  refreshTree(){
    let url = GlobalVariable.BASE_URL + 'institution/tree';
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
    let id = event.node.data.id;
    let url = GlobalVariable.BASE_URL + 'institution/find';
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
   * 编辑树形节点
   * @param type
   * @param id
   */
  editTreeNode(type: string, id: string) {
    this.router.navigate(['detail', {type: type, id: id}], {relativeTo: this.route});
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
          let url = GlobalVariable.BASE_URL + 'institution/delete';
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
