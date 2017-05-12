import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";

import {
    DataTableModule,
    SharedModule,
    PanelModule,
    ButtonModule,
    PaginatorModule,
    DialogModule,
    ConfirmDialogModule,
    InputTextModule,
    RadioButtonModule,
    PanelMenuModule,
    SplitButtonModule,
    MenuModule,
    DropdownModule,
    TreeModule,
    FileUploadModule,

    ConfirmationService
} from 'primeng/primeng';

@NgModule({
    imports: [
        // angular module
        CommonModule,
        BrowserModule,
        HttpModule,
        FormsModule,

        // primeng module
        DataTableModule,
        SharedModule,
        PanelModule,
        ButtonModule,
        PaginatorModule,
        DialogModule,
        ConfirmDialogModule,
        InputTextModule,
        RadioButtonModule,
        PanelMenuModule,
        SplitButtonModule,
        MenuModule,
        DropdownModule,
        TreeModule,
        FileUploadModule
    ],
    declarations: [],
    providers: [
        ConfirmationService
    ],
    exports: [
        // angular module
        CommonModule,
        BrowserModule,
        HttpModule,
        FormsModule,

        // primeng module
        DataTableModule,
        SharedModule,
        PanelModule,
        ButtonModule,
        PaginatorModule,
        DialogModule,
        ConfirmDialogModule,
        InputTextModule,
        RadioButtonModule,
        PanelMenuModule,
        SplitButtonModule,
        MenuModule,
        DropdownModule,
        TreeModule,
        FileUploadModule,
    ]
})
export class AppSharedModule {
}
