import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Http} from '@angular/http';
import {CookieService} from '../../service/cookie.service';
import {ClientVariable} from "../../client-variable";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private cookie: CookieService,
              private http: Http,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    /* 获取单点登录返回临时授权码 */
    this.route.queryParams.subscribe((params: Params) => {
      let code = params['code'];
      if (code) {
        this.setCookie(code); // 通过授权码获取用户信息
      } else {
        this.router.navigate([ClientVariable.indexUri]);
      }
    });
  }

  /**
   * 通过授权码获取accessToken
   * @param code
   */
  setCookie(code: string) {
    let url = 'accessToken?code=' + code + '&redirectUri='
      + encodeURIComponent(ClientVariable.redirectUri);
    this.http.get(url).toPromise().then(res => {
      let token = res.json();
      let expires: number = +token['expires_in'];
      // 设置token
      this.cookie.set('token', token, expires);
      // 判断redirectUri是否为空，如果为空则跳转至首页
      const redirectUri = localStorage.getItem('redirectUri');
      let url = redirectUri ? redirectUri : ClientVariable.indexUri;
      this.router.navigate([url]);
    });
  }

}
