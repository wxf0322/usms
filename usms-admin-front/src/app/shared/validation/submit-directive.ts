import {Directive, Input, OnInit, OnChanges, SimpleChanges, HostListener} from '@angular/core';
import {Validator, ValidatorFn, AbstractControl} from '@angular/forms';

@Directive({
  selector: '[validation-submit]',
})
export class SubmitDirective implements /*Validator,*/ OnInit {
  @Input("validation-submit") form: any;

  private validator: ValidatorFn;

  ngOnInit() {
    // console.log('init form', this.form);
  }

  // @HostListener('click', ['$event'])
  // onClick(e: Event) {
  //   console.log('e', e);
  //   this.vp.show();
  // }

  // validate(c: AbstractControl): { [key: string]: any } {
  //   console.log('control', c);
  // return this.validator(c);
  // }

  // registerOnValidatorChange(fn: () => void): void {
  //   this.onChange = fn;
  // }
}
