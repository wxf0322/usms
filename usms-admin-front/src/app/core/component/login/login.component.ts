import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {HttpService} from '../../service/http.service';
import {CookieService} from '../../service/cookie.service';
import {GlobalVariable} from "../../../shared/global-variable";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  code: string; // 单点登录返回临时授权码
  redirectUrl: string; // 重定向地址

  constructor(private cookie: CookieService,
              private http: HttpService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    if (this.cookie.get('user')) { // 已登录，跳转到首页
      this.router.navigate([GlobalVariable.indexUri]);
    } else { // 未登录
      /* 获取单点登录返回临时授权码 */
      this.route.queryParams.subscribe((params: Params) => {
        this.code = params['code'];
        if (this.code) {
          this.getUserInfo(this.code); // 通过授权码获取用户信息
        } else {
          this.router.navigate([GlobalVariable.indexUri]);
        }
      });
    }
  }

  /* 通过授权码获取用户信息 */
  getUserInfo(code: string) {
    this.http.findByParams('accessToken', {
      'code': code,
      'redirectUri': GlobalVariable.redirectUri
    }).then(res => {
      console.dir(res);
      this.cookie.set('user', JSON.stringify(res.data));
      const url = localStorage.getItem('redirectUrl');
      this.redirectUrl = url ? url : GlobalVariable.indexUri;
      this.router.navigate([this.redirectUrl]);
    });
  }

}
