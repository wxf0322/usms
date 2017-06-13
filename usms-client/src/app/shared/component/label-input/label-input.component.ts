import {Component, Input} from '@angular/core'

@Component({
  moduleId: module.id,
  selector:'eve-label-input',
  templateUrl:'./label-input.component.html'
})

export class EveLabelInputComponent {
  @Input() label: string;
  @Input() value: string;

  onInputChange(event:any){

  }
}
