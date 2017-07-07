import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {UsmsRoutingModule} from './usms-routing.module';
import {UsmsComponent} from './usms.component';
import {UserComponent} from './user/user.component';
import {RoleComponent} from './role/role.component';
import {PrivilegeComponent} from './privilege/privilege.component';
import {OperationComponent} from './operation/operation.component';
import {GridComponent} from './grid/grid.component';
import {ApplicationComponent} from './application/application.component';
import {OperationDetailComponent} from './operation/operation-detail/operation-detail.component';
import {InstitutionComponent} from './institution/institution.component';
import {InstitutionDetailComponent} from './institution/institution-detail/institution-detail.component';
import {InstitutionPanelComponent} from './institution/institution-panel/institution-panel.component';
import {InstitutionService} from './institution/institution.service';
import {UserAssociateComponent} from './grid/user-associate/user-associate.component';
import {RoleUserSelectorComponent} from './role/role-user-selector/role-user-selector.component';
import {GridUserSelectorComponent} from './grid/grid-user-selector/grid-user-selector.component';
import {ApplicationDialogComponent} from './application/application-dialog/application-dialog.component';
import {UserDialogComponent} from './user/user-dialog/user-dialog.component';
import {RoleDialogComponent} from './role/role-dialog/role-dialog.component';
import {PrivilegeDialogComponent} from './privilege/privilege-dialog/privilege-dialog.component';

/**
 * 统一用户管理根模块
 */
@NgModule({
  imports: [
    SharedModule, UsmsRoutingModule
  ],
  declarations: [
    UsmsComponent,
    UserComponent, UserDialogComponent,
    RoleComponent, RoleUserSelectorComponent, RoleDialogComponent,
    PrivilegeComponent, PrivilegeDialogComponent,
    OperationComponent, OperationDetailComponent,
    ApplicationComponent, ApplicationDialogComponent,
    InstitutionComponent, InstitutionPanelComponent, InstitutionDetailComponent,
    GridComponent, UserAssociateComponent, GridUserSelectorComponent,
  ],
  providers: [
    InstitutionService
  ]
})
export class UsmsModule {
}
