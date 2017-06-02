import {NgModule} from '@angular/core'
import {Routes, RouterModule} from '@angular/router';

export const routes: Routes = [
  {path: '', redirectTo: 'usms', pathMatch: 'full'},
  {path: 'nine-busi', loadChildren: './nine-busi/nine-busi.module#NineBusiModule'},
  {path: 'usms', loadChildren: './usms/usms.module#UsmsModule'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
