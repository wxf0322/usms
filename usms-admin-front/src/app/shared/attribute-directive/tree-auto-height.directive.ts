import {
  AfterViewChecked, AfterViewInit, Directive, ElementRef, HostListener, Input, Renderer2
} from '@angular/core';
/**
 * 根据指定类名的父元素高度，动态设置给定类名元素的高度，以达到动态设置滚动条目的。
 * （本指令只特殊处理primeng的datatable滚动条高度，由于angular会有很多自己的标签，所以需指定父类）
 * 用法：<div class="parentClassName"><div treeAutoHeight="parentClassName"></div></div>
 * 备注：需设置datatable的scrollable属性
 * @Author Warren Chan 2017年6月28日 11:14:00
 */
@Directive({
  selector: '[treeAutoHeight]'
})
export class TreeAutoHeightDirective implements AfterViewInit, AfterViewChecked {

  @Input('treeAutoHeight') parentClassName: string; // 指定父节点类名

  ele: Element; // 当前元素
  pEle: Element; // 父元素
  panelEle: Element; // 需要动态设置滚动条的元素
  deduct = 110; // 扣除高度（一般扣除分页条的高度，看需求自己写成输入属性）

  @HostListener('window:resize')onWindowResize() { // 正常使用本方法
  }

  constructor(private elementRef: ElementRef, private renderer2: Renderer2) {
    this.ele = elementRef.nativeElement;
  }

  getParentEle(ele: Element, className: string): Element { // 查询当前元素类名为某一个值的的祖先元素
    while (ele.tagName !== 'BODY') {
      ele = ele.parentElement;
      if (ele.className.indexOf(className) >= 0) {
        return ele;
      }
    }
  }

  ngAfterViewInit(): void { // 初始化完组件视图及其子视图
    this.pEle = this.getParentEle(this.ele, this.parentClassName); // 获取父元素
    this.panelEle = this.ele.querySelector('.ui-tree'); // 获取要动态设置滚动条元素
  }

  ngAfterViewChecked(): void {
    const pEleHeight = this.pEle.clientHeight;
    const scrollEleHeight = pEleHeight - this.deduct;
    this.renderer2.setStyle(this.panelEle, 'max-height', '');
    this.renderer2.setStyle(this.panelEle, 'height', scrollEleHeight + 'px');
  }

}
