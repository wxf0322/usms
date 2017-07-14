import {Component, OnInit, ViewChild} from "@angular/core";
import {TreeNode} from "primeng/primeng";
import {TreeUtil} from "../../../shared/util/tree-util";
import {StringUtil} from "../../../shared/util/string-util";
import {TreeData} from "../../../shared/util/tree-data";
import {HttpService} from "../../../core/service/http.service";
import {UserAssociateDialogComponent} from "../user-associate-dialog/user-associate-dialog.component";

@Component({
  selector: 'app-grid-panel',
  templateUrl: './grid-panel.component.html',
  styleUrls: ['./grid-panel.component.css']
})
export class GridPanelComponent implements OnInit {

  /**
   * 树形表查询关键字
   */
  tableQueryWord: string;

  /**
   * 树形表
   */
  tableTree: TreeNode[];

  /**
   * 选中的树形表节点
   */
  tableSelectedNode: TreeNode;

  /**
   * 用户弹框
   */
  @ViewChild(UserAssociateDialogComponent)
  userAssociateDialog : UserAssociateDialogComponent;

  constructor(public httpService: HttpService) {}

  ngOnInit(): void {
    this.refreshTree();
  }

  showDialog(node: any) {
    this.userAssociateDialog.showDialog(node.data.code);
  }

  queryTreeTable() {
    this.tableSelectedNode = TreeUtil.findNodesByLabel(this.tableTree, StringUtil.trim(this.tableQueryWord));
  }

  refreshTree() {
    this.tableTree = [];
    const url = 'grid/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tableTree = TreeUtil.buildTrees(treeDataArr);
        this.tableTree[0].expanded = true;
      });
  }

  onSaved(event) {
    this.refreshTree();
  }

}
