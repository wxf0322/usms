/*
 * 描述：特性区根模块
 * */
import {NgModule}      from '@angular/core';
import {SharedModule} from "../shared/shared.module";
import {NineBusiRoutingModule} from "./nine-busi-routing.module";
import {NineBusiComponent} from "./nine-busi.component";

@NgModule({
  imports: [
    SharedModule, NineBusiRoutingModule
  ],
  declarations: [
    NineBusiComponent
  ],
  providers: []
})
export class NineBusiModule {
}
