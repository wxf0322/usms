import {Component, OnInit, Output, ViewChild, EventEmitter} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Location} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {BaseDetail} from '../../../shared/util/base-detail';
import {HttpService} from '../../../core/service/http.service';
import {Application} from '../application';

@Component({
  selector: 'app-application-dialog',
  templateUrl: './application-dialog.component.html',
  styleUrls: ['./application-dialog.component.css']
})
export class ApplicationDialogComponent extends BaseDetail<Application> implements OnInit {

  /**
   * 表单验证
   */
  @ViewChild('reForm') reForm: NgForm;


  //客户端信息的显示与否
  clientInfoDisplay: boolean;

  /**
   * 绑定事件
   * @type {EventEmitter}
   */

  @Output() onSaved = new EventEmitter();

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Application();
  }

  ngOnInit(): void {
  }

  showDialog(type: string, id: string) {
    this.display = true;
    this.clientInfoDisplay = !(type === 'add');
    const url = 'application/find';
    this.initDialog(url, type, id).then(
      res => {
        if (res == false) {
          this.detailData = new Application();
          this.detailData.enabled = 1;
        }
      }
    );
  }

  goBack() {
    this.display = false;
  }

  save() {
    let url = (this.detailData.id == null) ? 'application/create' : 'application/update';
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '应用数据，' + this.detailData.label + '，更新或保存成功，'
        });
        this.goBack();
        this.onSaved.emit('refreshTable');
      });
  }

}

