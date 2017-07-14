import {AbstractControl, Validators, ValidatorFn, ValidationErrors} from '@angular/forms';

import { isPresent } from './lang';
/**
 * 正整数验证
 */
export const thirlength=ValidatorFn=>{
  return (control: AbstractControl): ValidationErrors => {
    if (isPresent(Validators.required(control))) return null;

    let v: string = control.value;
    return /^.{1,30}$/.test(v) ? null : {'length': true};
  }
};
