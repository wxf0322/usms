import {AbstractControl, Validators, ValidatorFn, ValidationErrors} from '@angular/forms';

export const required: ValidatorFn = () => {
  return (control: AbstractControl): ValidationErrors => {
    return Validators.required(control);
  };
};
