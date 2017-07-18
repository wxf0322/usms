import {Component, Input, OnInit} from '@angular/core';
import {HttpService} from "../../../core/service/http.service";
import {CookieService} from "../../../client/service/cookie.service";
import {ConfirmationService} from "primeng/primeng";
import {ClientVariable} from "../../../client/client-variable";

@Component({
  selector: 'eve-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class EveHeaderComponent implements OnInit {

  @Input() title: string; // 系统名称
  @Input() user: any; // 登录用户信息
  toggleState = 'off'; // 显示用户操作

  constructor(private httpService: HttpService,
              private cookie: CookieService,
              private confirmationService: ConfirmationService) {
  }

  ngOnInit() {
  }

  showUserOp() { // 显示用户更多操作
    this.toggleState = 'on';
  }

  onMouseleave() {
    this.toggleState = 'off';
  }

  /**
   * 登出
   */
  logout() {
    this.confirmationService.confirm({
      header: '退出系统',
      icon: 'fa fa-info-circle',
      message: '你确定要退出该系统吗。',
      accept: () => {
        this.cookie.deleteAll();
        this.httpService.findByParams('logout')
          .then(res => {
            window.location.href = ClientVariable.authUrl
              + '?client_id=' + ClientVariable.clientId
              + '&response_type=' + ClientVariable.responseType
              + '&redirect_uri=' + encodeURIComponent(ClientVariable.redirectUri);
          });
      },
      reject: () => {
      }
    });
  }

}

