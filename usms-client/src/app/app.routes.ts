import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const appRoutes: Routes = [
];

@NgModule({
    imports: [
        // router module
        RouterModule.forRoot(appRoutes, {useHash: true})
    ],
    exports: [
        RouterModule
    ]
})
export class AppRoutingModule {
}
