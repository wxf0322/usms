import {NgModule} from '@angular/core';
import {CaseLvlCdePipe, CaseTypePipe, SeriousCaseStatusPipe} from './serious-case.pipe';
import {IsOrNotPipe} from './isOrNot.pipe';
import {EnablePipe} from './enabled.pipe';
import {OperationTypePipe} from './operation-type.pipe';

@NgModule({
  declarations: [
    IsOrNotPipe, EnablePipe, OperationTypePipe,
    /*重大案件*/
    CaseLvlCdePipe, CaseTypePipe, SeriousCaseStatusPipe
  ],
  exports: [
    IsOrNotPipe, EnablePipe,
    CaseLvlCdePipe, CaseTypePipe, SeriousCaseStatusPipe
  ]
})

export class PipeModule {
}
