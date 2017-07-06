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


  // 双向绑定 dialogDisplay
  @Output() onSaved = new EventEmitter();

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
    this.detailData = new Application();
  }

  showDialog(type: string, id: string) {
    this.display = true;
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
    let url;
    if (this.detailData.id == null) {
      url = 'application/create';
    } else {
      url = 'application/update';
    }
    this.httpService.saveOrUpdate(url, this.detailData).then(
      res => {
        this.httpService.setMessage({
          severity: 'success',
          summary: '操作成功',
          detail: '成功' + this.detailData.name
        });
        this.goBack();
        this.onSaved.emit('refreshTable');
      });
  }

  ngOnInit(): void {
  }

}

