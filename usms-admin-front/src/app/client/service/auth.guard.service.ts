import {Injectable} from '@angular/core';
import {
  ActivatedRoute, ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router,
  RouterStateSnapshot
} from '@angular/router';
import {CookieService} from './cookie.service';
import {ConfirmationService} from 'primeng/primeng';
import {ClientVariable} from "../client-variable";

/**
 * 路由守卫
 * @author Warren Chan
 */
@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private cookie: CookieService,
              private confirmationService: ConfirmationService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    // 获取cookie中的令牌信息判断是否已登录
    if (this.cookie.get('token')) {
      return true;
    } else {
      // 保存用户访问地址
      localStorage.setItem('redirectUri', state.url);
      this.confirmationService.confirm({
        header: '未登录',
        icon: 'fa fa-info-circle',
        message: '您尚未登录，不能访问该功能。点击确定按钮跳转登录!',
        accept: () => {
          window.location.href = ClientVariable.authUrl
            + '?client_id=' + ClientVariable.clientId
            + '&response_type=' + ClientVariable.responseType
            + '&redirect_uri=' + encodeURIComponent(ClientVariable.redirectUri);
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
