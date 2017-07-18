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
    if (this.cookie.get('token')) { // 已登录，跳转到首页
      this.router.navigate([ClientVariable.indexUri]);
    } else { // 未登录
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
  }

  /**
   * 通过授权码获取accessToken
   * @param code
   */
  setCookie(code: string) {
    let url = 'accessToken?code=' + code
      + '&redirectUri=' + encodeURIComponent(ClientVariable.redirectUri);
    this.http.get(url).toPromise().then(res => {
      let token = res.json();
      let expires: number = +token['expires_in'];
      this.cookie.set('token', token, expires);
      const url = localStorage.getItem('redirectUri');
      let redirectUri = url ? url : ClientVariable.indexUri;
      this.router.navigate([redirectUri]);
    });
  }

}
