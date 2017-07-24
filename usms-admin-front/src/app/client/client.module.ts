import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ConfirmationService} from 'primeng/primeng';
import {CookieService} from "./service/cookie.service";
import {AuthGuard} from "./service/auth.guard.service";
import {LoginComponent} from "./component/login/login.component";
import {ClientService} from "./service/client.service";

@NgModule({
  imports: [RouterModule],
  declarations: [LoginComponent],
  exports: [LoginComponent],
  providers: [ConfirmationService, CookieService, ClientService, AuthGuard]
})
export class ClientModule {
}
