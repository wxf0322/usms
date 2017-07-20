import {Directive, Input, OnInit, OnChanges, SimpleChanges, HostListener} from '@angular/core';
import {Validator, ValidatorFn, AbstractControl} from '@angular/forms';

@Directive({
  selector: '[validation-submit]',
})
export class SubmitDirective implements /*Validator,*/ OnInit {
  @Input("validation-submit") form: any;

  private validator: ValidatorFn;

  ngOnInit() {
  }

}
