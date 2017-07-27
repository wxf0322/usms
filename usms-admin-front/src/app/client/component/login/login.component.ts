import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Http} from '@angular/http';
import {ClientVariable} from "../../client-variable";
import {ClientService} from "../../service/client.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private clientService: ClientService,
              private http: Http,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    // 获取单点登录返回临时授权码
    this.route.queryParams.subscribe((params: Params) => {
      let code = params['code'];
      if (code) {
        // 通过授权码获取用户信息
        this.setUserInfo(code);
      } else {
        this.router.navigate([ClientVariable.indexUri]);
      }
    });
  }

  /**
   * 通过授权码获取accessToken
   * @param code
   */
  setUserInfo(code: string) {
    let url = 'accessToken?code=' + code + '&redirectUri='
      + encodeURIComponent(ClientVariable.redirectUri);
    this.http.get(url).toPromise().then(res => {
      let user = res.json();
      let expiresIn: number = +user['expiresIn'];

      // 设置token
      localStorage.setItem('user', JSON.stringify(user));
      // 发送消息
      this.clientService.sendMessage(user);

      // 判断currentUrl是否为空，如果为空则跳转至首页
      const currentUrl = localStorage.getItem('currentUrl');
      let url = currentUrl ? currentUrl : ClientVariable.indexUri;
      this.router.navigate([url]);
    });
  }

}
