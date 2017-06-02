/*
 * 描述：特性区根模块
 * */
import {NgModule}      from '@angular/core';
import {SharedModule} from "../../shared/shared.module";
import {CmOrgRoutingModule} from "./cm-org-routing.module";
import {CmUnitComponent} from "./cm-unit/cm-unit.component";
import {CmMemberComponent} from './cm-member/cm-member.component';
import {CmUnitDetailComponent} from './cm-unit/cm-unit-detail/cm-unit-detail.component';
import {CmCenterDetailComponent} from './cm-center/cm-center-detail/cm-center-detail.component';
import {CmCenterComponent} from "./cm-center/cm-center.component";
import {NetInfoCenterComponent} from "./net-info-center/net-info-center.component";
import {SeriousCaseComponent} from './serious-case/serious-case.component';
import {SeriousCaseDetailComponent} from './serious-case/serious-case-detail/serious-case-detail.component';
import {NetInfoCenterDetailComponent} from "./net-info-center/net-info-center-detail/net-info-center-detail.component";
import {MpmtUnitComponent} from "./mpmt-unit/mpmt-unit.component";
import {MpmtUnitDetailComponent} from "./mpmt-unit/mpmt-unit-detail/mpmt-unit-detail.component";
@NgModule({
  imports: [
    SharedModule, CmOrgRoutingModule
  ],
  declarations: [
    /*综治机构*/
    CmUnitComponent, CmUnitDetailComponent,
    CmMemberComponent,
    /*综治中心*/
    CmCenterComponent, CmCenterDetailComponent,
    /*视联网信息中心*/
    NetInfoCenterComponent, NetInfoCenterDetailComponent,
    /*重特大案（事）件*/
    SeriousCaseComponent, SeriousCaseDetailComponent,
    /*综治-群防群治组织*/
    MpmtUnitComponent,MpmtUnitDetailComponent,
  ],
  providers: []
})
export class CmOrgModule {
}
