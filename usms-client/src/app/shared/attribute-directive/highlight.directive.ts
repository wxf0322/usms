import { Directive, ElementRef, Input, Renderer } from '@angular/core';
@Directive({ selector: '[eveHighlight]' })
export class EveHighlightDirective {
  constructor(el: ElementRef, renderer: Renderer) {
    console.log(el);
    renderer.setElementStyle(el.nativeElement, 'backgroundColor', 'yellow');
  }
}
