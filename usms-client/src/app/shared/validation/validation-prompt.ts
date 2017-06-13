/**
 * 表单验证结果的提示框
 */
import {ElementRef, Injectable} from "@angular/core";

@Injectable()
export class ValidationPrompt {

  enable: boolean;
  alertText: string = '格式错误';
  position: string = 'right';
  appendTo: any = 'body';
  positionStyle: string;
  validationStyle: string;
  validationStyleClass: string;
  alertTextStyleClass: string;

  private container: any;

  constructor(protected el: ElementRef) {
  }

  ngAfterViewChecked(): void {
    console.log('view');
    this.show();
  }

  show() {
    if (!this.enable) {
      // this.hide();
      return;
    }

    //已存在
    if (this.container && this.container.parentElement) return;

    this.create();

    let left: number;
    let top: number;

    let content = this.el.nativeElement;
    // console.log('content', content);
    let contentOffset = this.getOffset(content);

    //输入框的宽高
    let elWidth = this.getOuterWidth(content);
    let elHeight = this.getOuterHeight(content);

    //提示框的宽高
    let containerWidth = this.getOuterWidth(this.container);
    let containerHeight = this.getOuterHeight(this.container);

    //特殊处理，使用primeNG的下拉框时，真正的高度是其中的div
    if (content.querySelector('div')) {
      let outerChildHeight = this.getOuterHeight(content.querySelector('div'));
      elHeight = elHeight > outerChildHeight ? elHeight : outerChildHeight;
    }

    // console.log('offset width', this.getOuterWidth(content));
    // console.log('offset height', this.getOuterHeight(content));

    this.container.style.display = 'block';
    switch (this.position) {
      case 'right':
        left = contentOffset.left + elWidth;
        top = contentOffset.top;
        break;
      case 'left':
        left = contentOffset.left - containerWidth;
        top = contentOffset.top + (elHeight - containerHeight) / 2;
        break;
      case 'top':
        left = contentOffset.left + (elWidth - containerWidth) / 2;
        top = contentOffset.top - containerHeight;
        break;
      case 'bottom':
        left = contentOffset.left + (elWidth - containerWidth) / 2;
        top = contentOffset.top + elHeight;
        break;
    }
    this.container.style.left = left + 'px';
    this.container.style.top = top + 'px';
    this.container.style.zIndex = 2000;
    // console.log('container', this.container);
  }

  create() {
    let styleClass = 'validation-tip validation-tip-' + this.position;
    this.container = document.createElement('div');
    if (this.validationStyleClass) {
      styleClass += this.validationStyleClass;
    }

    this.container.className = styleClass;

    let arrow = document.createElement('div');
    arrow.className = 'validation-tip-arrow';
    this.container.appendChild(arrow);

    let alertTextDiv = document.createElement('div');
    let textStyleClass = 'validation-tip-text';
    if (this.alertTextStyleClass) {
      textStyleClass = textStyleClass + ' ' + this.alertTextStyleClass;
    }
    alertTextDiv.className = textStyleClass;

    alertTextDiv.appendChild(document.createTextNode(this.alertText));

    this.container.appendChild(alertTextDiv);

    if (this.appendTo === 'body')
      document.body.appendChild(this.container);
    else if (this.appendTo === 'target')
      this.appendChild(this.container, this.el.nativeElement);
    else
      this.appendChild(this.container, this.appendTo);
  }

  hide() {
    if (this.container && this.container.parentElement) {
      if (this.appendTo === 'body')
        document.body.removeChild(this.container);
      else if (this.appendTo === 'target')
        this.el.nativeElement.removeChild(this.container);
      else
        this.removeChild(this.container, this.appendTo);
    }
    this.container = null;
  }

  ngOnDestroy(): void {
    this.hide();
  }

  getOffset(el) {
    let x = el.offsetLeft;
    let y = el.offsetTop;
    // console.log('el.offsetParent', el.offsetParent);
    while (el = el.offsetParent) {
      x += el.offsetLeft;
      y += el.offsetTop;
    }
    return {left: x, top: y};
  }

  getOuterWidth(el, margin ?) {
    let width = el.offsetWidth;
    if (margin) {
      let style = getComputedStyle(el);
      width += parseFloat(style.marginLeft) + parseFloat(style.marginRight);
    }
    return width;
  }

  getOuterHeight(el, margin ?) {
    let height = el.offsetHeight;
    if (margin) {
      let style = getComputedStyle(el);
      height += parseFloat(style.marginTop) + parseFloat(style.marginBottom);
    }
    return height;
  }

  appendChild(element: any, target: any) {
    if (this.isElement(target))
      target.appendChild(element);
    else if (target.el && target.el.nativeElement)
      target.el.nativeElement.appendChild(element);
    else
      throw 'Cannot append ' + target + ' to ' + element;
  }

  removeChild(element: any, target: any) {
    if (this.isElement(target))
      target.removeChild(element);
    else if (target.el && target.el.nativeElement)
      target.el.nativeElement.removeChild(element);
    else
      throw 'Cannot remove ' + element + ' from ' + target;
  }

  isElement(obj: any) {
    return (typeof HTMLElement === "object" ? obj instanceof HTMLElement :
        obj && typeof obj === "object" && obj !== null && obj.nodeType === 1 && typeof obj.nodeName === "string"
    );
  }
}
