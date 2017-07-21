import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot, CanActivate, CanActivateChild, RouterStateSnapshot
} from '@angular/router';
import {CookieService} from "./cookie.service";
import {ClientVariable} from "../client-variable";

/**
 * 路由守卫
 * @author Warren Chan
 */
@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private cookie: CookieService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.cookie.get('user')) {
      return true;
    } else {
      // 保存用户访问地址
      localStorage.setItem('redirectUri', state.url);
      window.location.href = ClientVariable.authUrl
        + '?client_id=' + ClientVariable.clientId
        + '&response_type=' + ClientVariable.responseType
        + '&redirect_uri=' + encodeURIComponent(ClientVariable.redirectUri);
    }
  }

  canActivateChild() {
    return true;
  }

}
