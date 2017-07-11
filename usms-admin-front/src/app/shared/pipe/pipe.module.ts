import {NgModule} from '@angular/core';
import {CaseLvlCdePipe, CaseTypePipe, SeriousCaseStatusPipe} from './serious-case.pipe';
import {IsOrNotPipe} from './isOrNot.pipe';
import {EnablePipe} from './enabled.pipe';
import {OperationTypePipe} from './operation-type.pipe';
import {InstitutionPipe} from "./institution-type";

@NgModule({
  declarations: [
    IsOrNotPipe, EnablePipe, OperationTypePipe,InstitutionPipe,
    /*重大案件*/
    CaseLvlCdePipe, CaseTypePipe, SeriousCaseStatusPipe
  ],
  exports: [
    IsOrNotPipe, EnablePipe,InstitutionPipe,
    CaseLvlCdePipe, CaseTypePipe, SeriousCaseStatusPipe
  ]
})

export class PipeModule {
}
