import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {UserComponent} from './user/user.component';
import {UsmsComponent} from 'app/usms/usms.component';
import {RoleComponent} from './role/role.component';
import {OperationComponent} from './operation/operation.component';
import {PrivilegeComponent} from './privilege/privilege.component';
import {GridComponent} from './grid/grid.component';
import {ApplicationComponent} from './application/application.component';
import {UserDetailComponent} from './user/user-detail/user-detail.component';
import {PrivilegeDetailComponent} from './privilege/privilege-detail/privilege-detail.component';
import {RoleDetailComponent} from './role/role-detail/role-detail.component';
import {ApplicationDetailComponent} from './application/application-detail/application-detail.component';
import {OperationDetailComponent} from './operation/operation-detail/operation-detail.component';
import {InstitutionComponent} from './institution/institution.component';
import {InstitutionDetailComponent} from './institution/institution-detail/institution-detail.component';
import {InstitutionPanelComponent} from "./institution/institution-panel/institution-panel.component";
import {UserAssociateComponent} from "./grid/user-associate/user-associate.component";

/**
 * 页面组件路由
 */
const childrenRoutes: Routes = [
  {path: '', redirectTo: 'application', pathMatch: 'full'},
  {
    path: 'user',
    children: [
      {path: '', component: UserComponent},
      {path: 'user-detail', component: UserDetailComponent},
      {path: 'institution-detail', component: InstitutionDetailComponent}
    ]
  }, {
    path: 'institution',
    component: InstitutionComponent,
    children: [
      {path: '', redirectTo: 'panel', pathMatch: 'full'},
      {path: 'panel', component: InstitutionPanelComponent },
      {path: 'detail', component: InstitutionDetailComponent},
    ]
  },
  {
    path: 'role',
    children: [
      {path: '', component: RoleComponent},
      {path: 'detail', component: RoleDetailComponent},
    ]
  }, {
    path: 'operation',
    children: [
      {path: '', component: OperationComponent},
      {path: 'detail', component: OperationDetailComponent}
    ]
  }, {
    path: 'privilege',
    children: [
      {path: '', component: PrivilegeComponent},
      {path: 'detail', component: PrivilegeDetailComponent},
    ]
  }, {
    path: 'grid',
    children: [
      {path: '', component: GridComponent},
      {path: 'user-associate', component: UserAssociateComponent}
    ]
  }, {
    path: 'application',
    children: [
      {path: '', component: ApplicationComponent},
      {path: 'detail', component: ApplicationDetailComponent}
    ]
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
