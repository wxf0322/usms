/**
 * 核心模块：放置那些只在应用启动时导入一次的
 */
import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {RouterModule} from '@angular/router';
import {HttpService} from './service/http.service';
import {ConfirmationService} from 'primeng/primeng';
import {ClientModule} from "../client/client.module";
import {EveHeaderComponent} from "../shared/component/header/header.component";
import {SlideToggleComponent} from "../shared/component/slide-toggle/slide-toggle.component";

@NgModule({
  imports: [
    SharedModule, RouterModule, ClientModule
  ],
  declarations: [EveHeaderComponent, SlideToggleComponent],
  exports: [EveHeaderComponent, SlideToggleComponent],
  providers: [
    ConfirmationService, HttpService,
  ]
})
export class CoreModule {
}
