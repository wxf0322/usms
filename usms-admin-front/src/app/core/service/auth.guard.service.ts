import {Injectable} from '@angular/core';
import {
  ActivatedRoute, ActivatedRouteSnapshot, CanActivate, CanActivateChild, Params, Router,
  RouterStateSnapshot
} from '@angular/router';
import {CookieService} from './cookie.service';
import {GlobalVariable} from '../../shared/global-variable';
import {ConfirmationService} from 'primeng/primeng';

/**
 * 路由守卫
 * @author Warren Chan 2017年5月26日 10:38:33
 */
@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private cookie: CookieService,
              private route: ActivatedRoute,
              private router: Router,
              private confirmationService: ConfirmationService) {
  }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    // 获取cookie用户信息判断是否已登录
    if (this.cookie.get('user')) { // 已登录
      return true;
    } else {
      // 保存用户访问地址
      localStorage.setItem('redirectUrl', state.url);
      this.confirmationService.confirm({
        header: '未登录',
        icon: 'fa fa-info-circle',
        message: '您尚未登录，不能访问该功能。点击确定按钮跳转登录!',
        accept: () => {
          window.location.href = GlobalVariable.loginUrl
            + '?client_id=' + GlobalVariable.clientId
            + '&response_type=' + GlobalVariable.responseType
            + '&redirect_uri=' + GlobalVariable.redirectUri;
        },
        reject: () => {
        }
      });
    }
  }

  canActivateChild() {
    return true;
  }

}
