import {Component, OnInit, Renderer, ViewChild} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmationService, TreeNode} from "primeng/primeng";
import {BaseTable} from "../../../shared/util/base-table";
import {TreeData} from "../../../shared/util/tree-data";
import {TreeUtil} from "../../../shared/util/tree-util";
import {StringUtil} from "../../../shared/util/string-util";
import {HttpService} from "../../../core/service/http.service";
import {GridAssociateDialogComponent} from "../grid-associate-dialog/grid-associate-dialog.component";

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.css']
})
export class UserPanelComponent extends BaseTable<any> implements OnInit {

  /**
   * 组织机构名称
   */
  institutionName: string;

  /**
   * 组织机构Id
   */
  institutionId: number;

  /**
   * 组织机构树查询的关键字
   */
  instQueryWord: string;

  /**
   * 组织机构树
   */
  institutionTree: TreeNode[];

  /**
   * 选中的组织机构节点
   */
  instSelectedNode: TreeNode;

  /**
   * 网格关联弹框
   */
  @ViewChild(GridAssociateDialogComponent)
  gridAssociateDialog: GridAssociateDialogComponent;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
  }

  ngOnInit(): void {
    this.filter = '';
    this.refreshTree();
    this.getDataByPage(0, this.page.size, this.filter);
  }

  deleteSelected() {
  }

  /**
   * 弹框
   * @param type
   * @param id
   */
  showDialog(type: string, id: string) {
    this.gridAssociateDialog.showDialog(id);
  }

  /**
   * 查询框事件
   */
  query() {
    this.getDataByPage(0, this.page.size, this.filter);
  }

  /**
   * 分页
   * @param currentPage
   * @param rowsPerPage
   * @param filter
   */
  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
    const url = 'user/list?key=' + StringUtil.trim(this.filter);
    this.httpService.findByPage(url, currentPage, rowsPerPage, filter).then(
      res => this.setData(res)
    );
  }

  /**
   * 刷新组织机构树
   */
  refreshTree() {
    const url = 'institution/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.institutionTree = TreeUtil.buildTrees(treeDataArr);
        this.institutionTree[0].expanded = true;
      });
  }

  /**
   * 查询组织机构树
   */
  queryInstitution() {
    this.instSelectedNode = TreeUtil.findNodesByLabel(this.institutionTree, StringUtil.trim(this.instQueryWord));
    this.institutionName = this.instSelectedNode.label;
    const url = 'user/list?key=' + StringUtil.trim(this.filter);
    if (this.instSelectedNode.data.parentId !== 0) {
      this.institutionId = this.instSelectedNode.data.id;
    } else {
      this.institutionId = null;
    }
    const params = {institutionId: this.institutionId};
    this.httpService.findByPage(url, 0, this.page.size, params).then(
      res => this.setData(res)
    );
  }

  /**
   * 选中节点事件
   * @param event
   */
  nodeSelect(event) {
    this.institutionName = event.node.label;
    const url = 'user/list?key=' + StringUtil.trim(this.filter);
    if (event.node.data.parentId !== 0) {
      this.institutionId = event.node.data.id;
    } else {
      this.institutionId = null;
    }
    const params = {institutionId: this.institutionId};
    this.httpService.findByPage(url, 0, this.page.size, params).then(
      res => this.setData(res)
    );
  }

  /**
   * 保存事件
   * @param event
   */
  onSaved(event) {
    this.getDataByPage(this.page.number, this.page.size, this.filter);
  }

}
