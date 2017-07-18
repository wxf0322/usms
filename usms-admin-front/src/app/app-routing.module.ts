import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from "./client/component/login/login.component";

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'usms',
    pathMatch: 'full'
  }, {
    path: 'usms',
    loadChildren: './usms/usms.module#UsmsModule'
  }, {
    path: 'login',
    component: LoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
