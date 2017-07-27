import {Component, EventEmitter, Input, Output} from '@angular/core';
import {animate, group, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-slide-toggle',
  templateUrl: './slide-toggle.component.html',
  styleUrls: ['./slide-toggle.component.css'],
  animations: [
    trigger('toggleState', [
      state('on', style({})),
      state('off', style({
        padding: 0,
        display: 'none'
      })),
      transition('* => on', [
        style({width: 10, transform: 'translateX(50px)', opacity: 0}),
        group([
          animate('0.3s 0.1s ease', style({
            transform: 'translateX(0)',
            width: 120
          })),
          animate('0.3s ease', style({
            opacity: 1
          }))
        ])
      ]),
      transition('* => off', [])
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
