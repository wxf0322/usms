/**
 * 共享模块：所有模块共用的模块、组件、管道、指令等
 */
/*module*/
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
// PrimeNG
import {
  DataTableModule, InputTextModule, ButtonModule, DropdownModule, RadioButtonModule,
  PaginatorModule, PanelMenuModule, BreadcrumbModule, TabViewModule, DialogModule,
  ConfirmDialogModule, StepsModule, PanelModule, InputTextareaModule, CalendarModule,
  TabMenuModule, TreeModule, AutoCompleteModule, SplitButtonModule, SelectButtonModule,
  GrowlModule, TooltipModule, CheckboxModule, ContextMenuModule, TreeTableModule, ListboxModule,
  PickListModule, ToggleButtonModule, InputSwitchModule, InplaceModule
} from 'primeng/primeng';

import {BusyModule} from "tixif-ngx-busy";
import {EveLabelInputComponent} from './component/label-input/label-input.component';
import {SideMenuComponent} from './component/side-menu/side-menu.component';
import {ValidationRuleEngine} from './validation/validation-rule-engine';
import {SubmitDirective} from './validation/submit-directive';
import {PerfectScrollbarModule} from 'angular2-perfect-scrollbar';
import {PerfectScrollbarConfigInterface} from 'angular2-perfect-scrollbar';
import {PaginatorComponent} from './component/paginator/paginator.component';
import {TopBarComponent} from './component/top-bar/top-bar.component';
import {PipeModule} from './pipe/pipe.module';
import {CustomFormsModule} from 'ng2-validation';
import {CombotreeComponent} from './component/combotree/combotree.component';
import {TableAutoHeightDirective} from './attribute-directive/table-auto-height.directive';
import {DialogBarComponent} from './component/dialog-bar/dialog-bar.component';
import {TreeAutoHeightDirective} from './attribute-directive/tree-auto-height.directive';
import {DialogHeightDirective} from "./attribute-directive/dialog-height.directive";

/* directive */
/* pipe */
const PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

@NgModule({
  imports: [
    CommonModule, FormsModule, ReactiveFormsModule, HttpModule, RouterModule,
    TreeModule, ButtonModule, ListboxModule, ToggleButtonModule, PickListModule, InputSwitchModule,
    TreeModule, ButtonModule, SelectButtonModule, SplitButtonModule, DialogModule, TreeTableModule,
    PerfectScrollbarModule.forChild(), TabViewModule, CalendarModule, InplaceModule,
    // 管道
    PipeModule, BusyModule,
  ],
  declarations: [
    EveLabelInputComponent,
    SideMenuComponent,
    // 表单验证
    ValidationRuleEngine, SubmitDirective,
    // 分页
    PaginatorComponent, CombotreeComponent,
    TopBarComponent, DialogBarComponent,
    // directive：共享指令
    TableAutoHeightDirective,
    TreeAutoHeightDirective,
    DialogHeightDirective,
  ],
  exports: [
    // module：导出共享模块，导入本模块的模块无需重复导入这些模块，包括一些常用的第三方模块
    CommonModule, FormsModule, ReactiveFormsModule, HttpModule,
    // PrimeNg Module
    DataTableModule, InputTextModule, ButtonModule, DropdownModule, RadioButtonModule,
    PaginatorModule, PanelMenuModule, BreadcrumbModule, TabViewModule, DialogModule,
    ConfirmDialogModule, StepsModule, PanelModule, InputTextareaModule, CalendarModule,
    TabMenuModule, TreeModule, AutoCompleteModule, SplitButtonModule, SelectButtonModule,
    GrowlModule, TooltipModule, CheckboxModule, ContextMenuModule, ToggleButtonModule,
    PerfectScrollbarModule, ListboxModule, InputSwitchModule, PickListModule,
    InplaceModule, TreeTableModule,
    // directive：共享指令
    TableAutoHeightDirective,
    TreeAutoHeightDirective,
    // 共享组件
    EveLabelInputComponent, SideMenuComponent,
    // pipe：共享管道
    PipeModule, BusyModule,
    // 表单验证
    ValidationRuleEngine, SubmitDirective,
    // 分页
    PaginatorComponent,
    TopBarComponent, CombotreeComponent, DialogBarComponent,
    // ng2 validation
    CustomFormsModule,
    TableAutoHeightDirective,
    TreeAutoHeightDirective,
    DialogHeightDirective,
  ]
})
export class SharedModule {
}
