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
import {RoleUserSelectorComponent} from './role/role-user-selector/role-user-selector.component';
import {GridUserSelectorComponent} from './grid/grid-user-selector/grid-user-selector.component';
import {ApplicationDialogComponent} from './application/application-dialog/application-dialog.component';
import {UserDialogComponent} from './user/user-dialog/user-dialog.component';
import {RoleDialogComponent} from './role/role-dialog/role-dialog.component';
import {PrivilegeDialogComponent} from './privilege/privilege-dialog/privilege-dialog.component';
import {UserPanelComponent} from "./grid/user-panel/user-panel.component";
import {GridPanelComponent} from "./grid/grid-panel/grid-panel.component";
import {GridAssociateDialogComponent} from "./grid/grid-associate-dialog/grid-associate-dialog.component";
import {UserAssociateDialogComponent} from "./grid/user-associate-dialog/user-associate-dialog.component";
import {OperationPanelComponent} from "./operation/operation-panel/operation-panel.component";
import {OperationService} from "./operation/operation.service";

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
    OperationComponent, OperationDetailComponent, OperationPanelComponent,
    ApplicationComponent, ApplicationDialogComponent,
    InstitutionComponent, InstitutionPanelComponent, InstitutionDetailComponent,
    GridComponent, UserPanelComponent, GridPanelComponent,
    GridAssociateDialogComponent, UserAssociateDialogComponent, GridUserSelectorComponent,
  ],
  providers: [
    InstitutionService, OperationService
  ]
})
export class UsmsModule {
}
