import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {HttpService} from "../../../core/service/http.service";
import {GlobalVariable} from "../../../shared/global-variable";
import {User} from "../user";
import {TreeNode} from "primeng/primeng";
import {TreeUtil} from "../../../shared/util/tree-util";
import {TreeData} from "../../../shared/util/tree-data";


@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent extends SimpleBaseDetailUtil<User> implements OnInit {

  //表单验证
  @ViewChild('reForm') reForm: NgForm;

  display: boolean = false;

  /**
   * 树形节点
   */
  tree: TreeNode[];

  /**
   * 已经选中的节点
   */
  selectedNodes: TreeNode[];

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new User();
    this.detailData.enabled = 1;
    this.detailData.sex = 1;
  }

  setSelected() {
    const id = this.route.snapshot.params['id'];
    const selectedUrl = GlobalVariable.BASE_URL + 'user/institutions';
    const params = {userId: id};
    this.selectedNodes = [];
    this.httpService.findByParams(selectedUrl, params)
      .then(res => {
        TreeUtil.setSelection(this.tree, this.selectedNodes, res);
      });
  }

  refreshTree() {
    let url = GlobalVariable.BASE_URL + 'institution/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
        this.setSelected();
      });
  }

  ngOnInit(): void {
    let url = GlobalVariable.BASE_URL + 'user/find';
    this.init(url);
    this.refreshTree();
  }

  goBack() {
    this.location.back();
  }

  save() {
    let url = GlobalVariable.BASE_URL + 'user/saveOrUpdate';
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功更新' + this.detailData.name
        });
        this.goBack();
      });
  }

  showDialog() {
    this.display = true;
  }

}
