import {NgModule} from '@angular/core'
import {Routes, RouterModule} from "@angular/router";
import {NineBusiComponent} from "./nine-busi.component";

/*
 * 九大业务路由
 * */
export const nineBusiRoutes: Routes = [
  {
    path: '', component: NineBusiComponent,
    children: [
      {path: '', redirectTo: 'cm-org', pathMatch: 'full'},
      {path: 'cm-org', loadChildren: './cm-org/cm-org.module#CmOrgModule'},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(nineBusiRoutes)],
  exports: [RouterModule]
})
export class NineBusiRoutingModule {
}
