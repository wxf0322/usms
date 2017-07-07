import {Component, OnInit, Renderer, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ConfirmationService, TreeNode} from 'primeng/primeng';
import {TreeData} from '../../shared/util/tree-data';
import {TreeUtil} from '../../shared/util/tree-util';
import {HttpService} from '../../core/service/http.service';
import {BaseTable} from '../../shared/util/base-table';
import {BeanUtil} from '../../shared/util/bean-util';
import {UserAssociateComponent} from './user-associate/user-associate.component';
import {StringUtil} from "../../shared/util/string-util";

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent extends BaseTable<any> implements OnInit {

  /**
   * 组织机构树查询的关键字
   */
  instQueryWord: string;
  /**
   * 树形表查询关键字
   */
  tableQueryWord: string;
  /**
   * 组织机构名称
   */
  institutionName: string;
  /**
   * 组织机构名称
   */
  institutionId: number;
  /**
   * 树形节点
   */
  institutionTree: TreeNode[];
  /**
   * 用户id
   */
  userId: string;
  /**
   * 关联网格树的回填资源
   */
  selectedNodes: TreeNode[] = [];
  /**
   * 选中的节点
   */
  tableSelectedNode: TreeNode;
  /**
   * 选中的组织机构节点
   */
  instSelectedNode: TreeNode;
  /**
   * 弹框树
   */
  dialogTree: TreeNode[];
  /**
   * 树形表
   */
  tableTree: TreeNode[];
  /**
   * 是否显示弹框
   */
  display: boolean;

  @ViewChild(UserAssociateComponent)
  userAssociate: UserAssociateComponent;

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
  }

  ngOnInit(): void {
    this.refreshTree();
    this.refreshGridTree();
    this.filter = '';
    this.getDataByPage(0, this.page.size, this.filter);
  }

  refreshGridTree() {
    const url = 'grid/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tableTree = TreeUtil.buildTrees(treeDataArr);
        this.tableTree[0].expanded = true;
        this.dialogTree = BeanUtil.arrayDeepCopy(this.tableTree);
      });
  }

  /**
   * 获取组织机构树
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

  deleteSelected() {
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
    const url = 'user/list?key=' + StringUtil.trim(this.filter);
    this.httpService.findByPage(url, currentPage, rowsPerPage, filter).then(
      res => this.setData(res)
    );
  }

  showUserDialog(node: any) {
    this.userAssociate.showDialog(node.data.code);
  }

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

  query() {
    this.getDataByPage(0, this.page.size, this.filter);
  }

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

  queryTreeTable() {
    this.tableSelectedNode = TreeUtil.findNodesByLabel(this.tableTree, StringUtil.trim(this.tableQueryWord));
  }

  showDialog(id: string) {
    this.display = true;
    this.userId = id;
    this.dialogTreeInit();
  }

  dialogTreeInit() {
    this.selectedNodes = [];
    const url = 'user/grids';
    const params = {
      userId: this.userId
    };
    this.httpService.findByParams(url, params)
      .then(res => {
        // 回填已选中的网格数据
        TreeUtil.setSelection(this.dialogTree, this.selectedNodes, res);
      });
  }

  save() {
    const gridCodes = this.selectedNodes.map(node => node.data.code).join(',');
    const params = {
      userId: this.userId,
      gridCodes: gridCodes
    };
    const url = 'grid/updateGrids';
    this.httpService.executeByParams(url, params).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功更新'
        });
        this.display = false;
        this.getDataByPage(this.page.number, this.page.size, this.filter)
      });
  }

  cancel() {
    this.display = false;
  }

  onSaved(event) {
    this.refreshGridTree();
  }

}

