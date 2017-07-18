import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {UserComponent} from './user/user.component';
import {UsmsComponent} from 'app/usms/usms.component';
import {RoleComponent} from './role/role.component';
import {OperationComponent} from './operation/operation.component';
import {PrivilegeComponent} from './privilege/privilege.component';
import {GridComponent} from './grid/grid.component';
import {ApplicationComponent} from './application/application.component';
import {OperationDetailComponent} from './operation/operation-detail/operation-detail.component';
import {InstitutionComponent} from './institution/institution.component';
import {InstitutionDetailComponent} from './institution/institution-detail/institution-detail.component';
import {InstitutionPanelComponent} from './institution/institution-panel/institution-panel.component';
import {AuthGuard} from "../client/service/auth.guard.service";

/**
 * 页面组件路由
 */
const childrenRoutes: Routes = [
  {
    path: '',
    redirectTo: 'application',
    pathMatch: 'full'
  }, {
    path: 'user',
    component: UserComponent
  }, {
    path: 'institution',
    component: InstitutionComponent,
    children: [
      {path: '', redirectTo: 'panel', pathMatch: 'full'},
      {path: 'panel', component: InstitutionPanelComponent},
      {path: 'detail', component: InstitutionDetailComponent}
    ]
  },
  {
    path: 'role',
    component: RoleComponent
  }, {
    path: 'operation',
    component: OperationComponent,
    children: [
      {path: 'detail', component: OperationDetailComponent}
    ]
  }, {
    path: 'privilege',
    canActivate: [AuthGuard],
    component: PrivilegeComponent
  }, {
    path: 'grid',
    component: GridComponent
  }, {
    path: 'application',
    canActivate: [AuthGuard],
    component: ApplicationComponent
  }
];

/**
 * 统一用户管理根路由
 */
const usmsRoutes: Routes = [
  {path: '', component: UsmsComponent, children: childrenRoutes}
];

@NgModule({
  imports: [RouterModule.forChild(usmsRoutes)],
  exports: [RouterModule]
})
export class UsmsRoutingModule {
}
