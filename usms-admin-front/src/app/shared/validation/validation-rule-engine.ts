/**
 * 调用定义的规则进行验证
 */
import {Directive, Input, forwardRef, OnInit, OnChanges, SimpleChanges, ElementRef, HostListener} from '@angular/core';
import {NG_VALIDATORS, Validator, ValidatorFn, AbstractControl, Validators} from '@angular/forms';
import {CustomValidators} from "./custom-validators";
import {ValidationPrompt} from "./validation-prompt";

const RULE_VALIDATOR: any = {
  provide: NG_VALIDATORS,
  useExisting: forwardRef(() => ValidationRuleEngine),
  multi: true
};

@Directive({
  selector: '[validation-rule][formControlName],[validation-rule][formControl],[validation-rule][ngModel]',
  providers: [RULE_VALIDATOR, ValidationPrompt]
})
export class ValidationRuleEngine implements Validator, OnInit, OnChanges {
  @Input("validation-rule") rules: any;
  state;
  private onChange: () => void;

  constructor(private el: ElementRef,
              private vp: ValidationPrompt) {
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges) {
    let change = changes['rules'];
    if (change) {
      for (let key in this.rules) {
        let rule = this.rules[key];
        let value = rule['value'];
        let fn = CustomValidators[key];
        rule['fn'] = fn(value);
      }
      if (this.onChange) this.onChange();
    }
  }

  @HostListener('mouseenter', ['$event'])
  onMouseEnter(e: Event) {
    //console.log('enter');
    this.vp.show();
  }

  @HostListener('mouseleave', ['$event'])
  onMouseLeave(e: Event) {
    //console.log('leave');
    if (this.state === 'focus') return;
    this.vp.hide();
  }

  @HostListener('focus', ['$event'])
  onFocus(e: Event) {
    //console.log('focus');
    this.state = 'focus';
    this.vp.show();
  }

  @HostListener('blur', ['$event'])
  onBlur(e: Event) {
    //console.log('blur');
    this.state = 'blur';
    this.vp.hide();
  }

  validate(c: AbstractControl): { [key: string]: any } {
    let result = null;
    for (let key in this.rules) {
      let rule = this.rules[key];
      let fn = rule['fn'];
      result = fn(c);
      this.vp.enable = false;
      this.vp.hide();
      if (result) {
        this.vp.alertText = rule['alertText'];
        if (!c.dirty && key === 'required') {
          return result;
        }

        this.vp.enable = true;
        this.vp.show();
        return result;
      }
    }
    return result;
  }

  registerOnValidatorChange(fn: () => void): void {
    this.onChange = fn;
  }
}
