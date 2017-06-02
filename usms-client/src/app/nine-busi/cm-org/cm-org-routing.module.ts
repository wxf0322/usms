import {NgModule} from '@angular/core'
import {Routes, RouterModule} from "@angular/router";
import {CmUnitComponent} from "./cm-unit/cm-unit.component";
import {CmUnitDetailComponent} from "./cm-unit/cm-unit-detail/cm-unit-detail.component";
import {CmMemberComponent} from "./cm-member/cm-member.component";
import {CmCenterComponent} from "./cm-center/cm-center.component";
import {CmCenterDetailComponent} from "./cm-center/cm-center-detail/cm-center-detail.component";
import {NetInfoCenterComponent} from "./net-info-center/net-info-center.component";
import {SeriousCaseComponent} from "./serious-case/serious-case.component";
import {SeriousCaseDetailComponent} from "./serious-case/serious-case-detail/serious-case-detail.component";
import {NetInfoCenterDetailComponent} from "./net-info-center/net-info-center-detail/net-info-center-detail.component";
import {MpmtUnitComponent} from "./mpmt-unit/mpmt-unit.component";
import {MpmtUnitDetailComponent} from "./mpmt-unit/mpmt-unit-detail/mpmt-unit-detail.component";

/*
 * 特性区路由
 * */
export const cmOrgRoutes: Routes = [
  {path: '', redirectTo: 'cm-unit'},
  {
    path: 'cm-unit',
    children: [
      {path: '', component: CmUnitComponent},
      {path: 'detail', component: CmUnitDetailComponent}
    ]
  },
  {
    path: 'cm-center',
    children: [
      {path: '', component: CmCenterComponent},
      {path: 'detail', component: CmCenterDetailComponent}
    ]
  },
  {
    path: 'net-info-center',
    children: [
      {path: '', component: NetInfoCenterComponent},
      {path: 'detail', component: NetInfoCenterDetailComponent}
    ]
  },
  {path: 'cm-member', component: CmMemberComponent},
  {
    path: 'serious-case',
    children: [
      {path: '', component: SeriousCaseComponent},
       {path: 'detail', component: SeriousCaseDetailComponent}
    ]
  },
  {
    path: 'mpmt-unit',
    children: [
      {path: '', component: MpmtUnitComponent},
      {path: 'detail', component: MpmtUnitDetailComponent}
    ]
  }
];
@NgModule({
  imports: [RouterModule.forChild(cmOrgRoutes)],
  exports: [RouterModule]
})

export class CmOrgRoutingModule {


}
