/**
 * 核心模块：放置那些只在应用启动时导入一次的
 */
import {NgModule, SkipSelf, Optional} from '@angular/core';
import {EveHeaderComponent} from '../shared/component/nav/header.component';
import {SharedModule} from '../shared/shared.module';
import {RouterModule} from '@angular/router';

import {ConfirmationService} from 'primeng/primeng';
import {HttpService} from './service/http.service';
@NgModule({
  imports: [
    SharedModule, RouterModule
  ],
  declarations: [EveHeaderComponent],
  exports: [EveHeaderComponent],
  providers: [
    ConfirmationService, HttpService
  ]
})
export class CoreModule {
}
