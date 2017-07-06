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


@Component({
  selector: 'app-user-dialog',
  templateUrl: './user-dialog.component.html',
  styleUrls: ['./user-dialog.component.css']
})
export class UserDialogComponent extends BaseDetail<any> implements OnInit {

  // 表单验证
  @ViewChild('reForm') reForm: NgForm;

  date: Date;

  sourceRoles: Role[] = [];

  targetRoles: Role[] = [];

  tree: TreeNode[] = [];

  /**
   * 空图片
   * @type {string}
   */
  nullPicture = 'static/images/null.png';

  /**
   * 回填机构树的数据
   */
  selectedNodes: TreeNode[] = [];

  /**
   * 回填的名称
   */
  selectedNames: string;

  /**
   * 主键id
   */
  id: string;

  /**
   * 时间相关汉化
   */
  en:any;

  // 双向绑定 onSaved
  @Output() onSaved = new EventEmitter();


  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute,
              protected elementRef: ElementRef,
              protected http: Http,) {
    super(httpService, route);
    this.detailData = new User();
    this.detailData.pictureUrl = this.nullPicture;

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

  ngOnInit(): void {
    this.en = {
      firstDayOfWeek: 0,
      dayNames: ["日", "一", "二", "三", "四", "五", "六"],
      dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
      dayNamesMin: ["日", "一", "二", "三", "四", "五", "六"],
      monthNames: [ "一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月" ],
      monthNamesShort: [ "Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ]
    };
  }

  showDialog(type: string, id: string) {
    this.display = true;
    this.detailData.pictureUrl = this.nullPicture;
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
    this.id = id;
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
          detail: '成功更新' + this.detailData.name
        });
        this.goBack();
        this.onSaved.emit('refreshTable');
      });
  }

  roleInit() {
    let id = this.id;
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
    let id = this.id;
    const url = 'user/institutions';
    const params = {userId: id};
    this.httpService.findByParams(url, params)
      .then(res => {
        this.selectedNames = res.map(node => node.label).join(',');
        // 回填已选中的网格数据
        TreeUtil.setSelection(this.tree, this.selectedNodes, res);
      });
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
        data => {
          this.detailData.pictureUrl = data['filename'];
        },
        error => console.log(error)
      );
  }





}
