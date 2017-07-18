import {Component, OnInit, Output, EventEmitter, ViewChild, ElementRef} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Location} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {BaseDetail} from '../../../shared/util/base-detail';
import {HttpService} from '../../../core/service/http.service';
import {User} from '../user';
import {Role} from '../../role/role';
import {TreeData} from '../../../shared/util/tree-data';
import {TreeUtil} from '../../../shared/util/tree-util';
import {TreeNode} from 'primeng/primeng';
import {RequestOptions, Http, Headers} from '@angular/http';
import {Observable} from 'rxjs';
import {GlobalVariable} from "../../../shared/global-variable";

@Component({
  selector: 'app-user-dialog',
  templateUrl: './user-dialog.component.html',
  styleUrls: ['./user-dialog.component.css']
})
export class UserDialogComponent extends BaseDetail<any> implements OnInit {

  /**
   * 表单验证
   */
  @ViewChild('reForm') reForm: NgForm;

  /**
   * 未选中的角色
   * @type {Array}
   */
  sourceRoles: Role[] = [];

  /**
   * 已选中的角色
   * @type {Array}
   */
  targetRoles: Role[] = [];

  /**
   * 用户生日数据
   */
  date: Date;

  /**
   * 当前用户所属的组织机构树
   * @type {Array}
   */
  tree: TreeNode[] = [];

  /**
   * 空图片
   * @type {string}
   */
  nullPicture = 'static/images/null.png';

  /**
   * 回填机构树的数据
   * @type {Array}
   */
  selectedNodes: TreeNode[] = [];

  /**
   * 回填的名称
   */
  selectedNames: string;

  /**
   * 用户id
   */
  userId: string;

  /**
   * 时间相关汉化
   */
  en: any;

  /**
   * 绑定事件
   */
  @Output() onSaved = new EventEmitter();

  /**
   * 类型
   */
  type: string;

  /**
   * 当前组织机构ID
   */
  institutionId: number;
  /**
   * 当前组织机构名称
   */
  institutionName: string;

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute,
              protected elementRef: ElementRef,
              protected http: Http,) {
    super(httpService, route);
    this.detailData = new User();

  }


  ngOnInit(): void {
    this.en = GlobalVariable.locale;
  }

  refreshTree() {
    const url = 'institution/tree';
    let treeDataArr: TreeData[];
    this.httpService.findByParams(url)
      .then(res => {
        treeDataArr = res;
        this.tree = TreeUtil.buildTrees(treeDataArr);
        this.tree[0].expanded = true;
        this.setInstitutions();
      });
  }

  showDialog(type: string, id: string, institutionId: number, institutionName: string) {
    this.display = true;
    this.detailData.pictureUrl = this.nullPicture;

    this.type = type;
    this.institutionId = institutionId;
    this.institutionName = institutionName;
    const url = 'user/find';
    this.initDialog(url, type, id).then(
      res => {

        if (this.detailData.birthday != null) {
          this.date = new Date(this.detailData.birthday);
        } else {
          this.date = new Date();
        }
        if (this.detailData.pictureUrl == null) {
          this.detailData.pictureUrl = this.nullPicture;
        }
        if (res == false) {
          this.detailData = new User();
          this.detailData.enabled = 1;
          this.detailData.sex = 1;
        }
      });
    this.userId = id;
    this.refreshTree();
    this.roleInit();
  }

  goBack() {
    this.display = false;
    this.selectedNames = '';
    this.selectedNodes = [];
  }

  save() {
    this.detailData.birthday = this.date.getTime();
    this.detailData.roleIds = '';
    this.detailData.roleIds = this.targetRoles.map(role => role.id).join(',');
    this.detailData.institutionIds = this.selectedNodes.map(node => node.data.id).join(',');
    const url = 'user/saveOrUpdate';
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '用户数据，' + this.detailData.loginName + '，更新或保存成功'
        });
        this.goBack();
        this.onSaved.emit('refreshTable');
      });
  }

  roleInit() {
    let id = this.userId;
    let targetUrl = 'user/roles/target';
    let sourceUrl = 'user/roles/source';
    if (id === 'null') {
      id = '';
    }
    let params = {userId: id};
    this.httpService.executeByParams(sourceUrl, params).then(
      res => this.sourceRoles = res
    );
    this.httpService.executeByParams(targetUrl, params).then(
      res => this.targetRoles = res
    );
  }

  setInstitutions() {
    if (this.type === 'add') {
      TreeUtil.setSelection(this.tree, this.selectedNodes, [{id: this.institutionId}]);
      this.selectedNames = this.institutionName;
    } else {
      let id = this.userId;
      const url = 'user/institutions';
      const params = {userId: id};
      this.httpService.findByParams(url, params)
        .then(res => {
          this.selectedNames = res.map(node => node.label).join(',');
          // 回填已选中的网格数据
          TreeUtil.setSelection(this.tree, this.selectedNodes, res);
        });
    }
  }

  fileSelected() {
    let url = 'file/upload';
    let oFile = this.elementRef.nativeElement.querySelector('#imageFile').files[0];
    let formData: FormData = new FormData();
    formData.append('uploadFile', oFile, oFile.name);
    let headers = new Headers({'Accept': 'application/json'});
    let options = new RequestOptions({headers});
    this.http.post(url, formData, options)
      .map(res => res.json())
      .catch(error => Observable.throw(error))
      .subscribe(
        data => this.detailData.pictureUrl = data['filename'],
        error => console.log(error)
      );
  }

}
