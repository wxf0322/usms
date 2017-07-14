/**
 * Created by thinkpad on 2017-7-12.
 */
import {
  AfterViewChecked, AfterViewInit, Directive, ElementRef, HostListener, Input, Renderer2
} from '@angular/core';

@Directive({
  selector: '[dialogHeight]'
})
export class DialogHeightDirective implements AfterViewInit, AfterViewChecked {

  @Input('dialogHeight') dialogHeight: number; // 指定父节点类名
  ele: Element; // 当前元素
  dialogEle: Element; // 需要动态设置滚动条的元素

  constructor(private elementRef: ElementRef, private renderer2: Renderer2) {
    this.ele = elementRef.nativeElement;
  }

  ngAfterViewInit(): void {
    this.dialogEle = this.ele.querySelector('.ui-dialog-content'); // 获取要动态设置滚动条元素
  }

  @HostListener('window:resize')onWindowResize() { // 正常使用本方法
  }

  ngAfterViewChecked(): void {
  /* const pEleHeight = this.pEle.clientHeight;
    const scrollEleHeight = pEleHeight - this.deduct;*/
    this.renderer2.setStyle(this.dialogEle, 'max-height', this.dialogHeight + 'px');
    this.renderer2.setStyle(this.dialogEle, 'height', this.dialogHeight + 'px');
  }

}
