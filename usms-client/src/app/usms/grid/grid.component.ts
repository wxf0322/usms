import {Component, OnInit, Renderer} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmationService, TreeNode} from 'primeng/primeng';
import {TreeData} from "../../shared/util/tree-data";
import {TreeUtil} from "../../shared/util/tree-util";
import {HttpService} from "../../core/service/http.service";
import {SimpleBaseUtil} from "../../shared/util/simple-base.util";
import {User} from "../user/user";
import {BeanUtil} from "../../shared/util/bean-util";

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent extends SimpleBaseUtil<any> implements OnInit {

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

  selectedNode: TreeNode[];
  /**
   * 弹框树
   */
  dialogTree: TreeNode[];

  /**
   * 树形表
   */
  tableTree: TreeNode[];

  display: boolean;
  /**
   * 选中的网格编号
   */
  gridCode: string = '';

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super(router, route, httpService, confirmationService, renderer);
  }

  ngOnInit(): void {
    this.refreshTree();
    const url = 'grid/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tableTree = TreeUtil.buildTrees(treeDataArr);
        // this.tableTree = BeanUtil.arrayDeepCopy(this.gridTree);
        this.dialogTree = BeanUtil.arrayDeepCopy(this.tableTree);
      });
    this.filter = '';
    this.getDataByPage(0, this.page.size, this.filter);
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
      });
  }

  deleteSelected() {
  }

  getDataByPage(currentPage: any, rowsPerPage: any, filter: any) {
    const url = 'grid/list?gridCode=' + this.gridCode;
    this.httpService.findByPage(url, currentPage, rowsPerPage, filter).then(
      res => {
        return this.setData(res);
      }
    );
  }

  gotoUserAssociate(node: any) {
    this.router.navigate(['user-associate', {code: node.data.code}], {relativeTo: this.route});
  }


  nodeSelect(event) {
    this.institutionName = event.node.label;
    let url = 'user/list?key=' + this.filter;
    if (event.node.data.parentId != 0) {
      this.institutionId = event.node.data.id;
    } else {
      this.institutionId = null;
    }
    let params = {
      institutionId: this.institutionId
    };
    this.httpService.findByPage(url, 0, this.page.size, params).then(
      res => this.setData(res)
    );
  }

  query() {
    const url = 'user/list?key=' + this.filter;
    let params = {institutionId: this.institutionId};
    this.httpService.findByPage(url, 0, this.page.size, params).then(
      res => {
        return this.setData(res);
      }
    );
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
        //回填已选中的网格数据
        TreeUtil.setSelection(this.dialogTree, this.selectedNodes, res);
      });
  }

  save() {
    let gridCodes = '';
    for (let i in this.selectedNodes) {
      gridCodes =
        gridCodes + this.selectedNodes[i].data.code + ',';
    }
    let params = {
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
      });
  }

  cancel() {
    this.display = false;
  }

}


