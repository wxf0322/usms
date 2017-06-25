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
import {UserDetailComponent} from './user/user-detail/user-detail.component';
import {PrivilegeDetailComponent} from './privilege/privilege-detail/privilege-detail.component';
import {RoleDetailComponent} from './role/role-detail/role-detail.component';
import {ApplicationDetailComponent} from './application/application-detail/application-detail.component';
import {OperationDetailComponent} from './operation/operation-detail/operation-detail.component';
import {InstitutionComponent} from './institution/institution.component';
import {InstitutionDetailComponent} from './institution/institution-detail/institution-detail.component';
import {InstitutionPanelComponent} from './institution/institution-panel/institution-panel.component';
import {InstitutionService} from './institution/institution.service';
import {UserAssociateComponent} from './grid/user-associate/user-associate.component';
import {RoleUserSelectorComponent} from "./role/role-user-selector/role-user-selector.component";
import {GridUserSelectorComponent} from "./grid/grid-user-selector/grid-user-selector.component";

/**
 * 统一用户管理根模块
 */
@NgModule({
  imports: [
    SharedModule, UsmsRoutingModule
  ],
  declarations: [
    UsmsComponent,
    UserComponent, UserDetailComponent,
    RoleComponent, RoleDetailComponent,RoleUserSelectorComponent,
    PrivilegeComponent, PrivilegeDetailComponent,
    OperationComponent, OperationDetailComponent,
    ApplicationComponent, ApplicationDetailComponent,
    InstitutionComponent, InstitutionPanelComponent, InstitutionDetailComponent,
    GridComponent, UserAssociateComponent,GridUserSelectorComponent,
  ],
  providers: [
    InstitutionService
  ]
})
export class UsmsModule {
}
