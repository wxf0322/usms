/**
 * 核心模块：放置那些只在应用启动时导入一次的
 */
import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {RouterModule} from '@angular/router';
import {HttpService} from './service/http.service';
import {ConfirmationService} from 'primeng/primeng';
import {EveHeaderComponent} from '../shared/component/nav/header.component';
import {CookieService} from "./service/cookie.service";
import {AuthGuard} from "./service/auth.guard.service";
import {LoginComponent} from "./component/login/login.component";

@NgModule({
  imports: [
    SharedModule, RouterModule
  ],
  declarations: [EveHeaderComponent, LoginComponent],
  exports: [EveHeaderComponent, LoginComponent],
  providers: [
    ConfirmationService, HttpService, CookieService, AuthGuard
  ]
})
export class CoreModule {
}
