import {Component, EventEmitter, Input, Output} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-slide-toggle',
  templateUrl: './slide-toggle.component.html',
  styleUrls: ['./slide-toggle.component.css'],
  animations: [
    trigger('toggleState', [
      state('on', style({})),
      state('off', style({
        maxHeight: 0,
        padding: 0,
        display: 'none'
      })),
      transition('* => on', animate('500ms')),
      transition('* => off', animate('0ms'))
    ])
  ]
})
export class SlideToggleComponent {
  @Input() direction = 'v'; // 滑动方向v(vertical垂直)，h(horizontal水平)
  @Input() toggleState: string; // 滑动开关0关1开
  @Input() label: string; // 显示文字
  @Input() width = '80px'; // 面板宽度
  @Output() mouseleave = new EventEmitter();

  constructor() {
  }

  onMouseleave() {
    this.toggleState = 'off';
  }

}
