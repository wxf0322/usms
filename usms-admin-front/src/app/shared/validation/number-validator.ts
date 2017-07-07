import {AbstractControl, Validators, ValidatorFn, ValidationErrors} from '@angular/forms';

import { isPresent } from './lang';

/**
 * 正数
 */
export const number=ValidatorFn=>{
  return (control: AbstractControl): ValidationErrors => {
    if (isPresent(Validators.required(control))) return null;

    let v: string = control.value;
    return /^(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(v) ? null : {'number': true};
  }
};