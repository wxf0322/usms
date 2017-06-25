import { Directive, ElementRef, Renderer } from '@angular/core';
@Directive({ selector: '[eveHighlight]' })
export class EveHighlightDirective {
  constructor(el: ElementRef, renderer: Renderer) {
    renderer.setElementStyle(el.nativeElement, 'backgroundColor', 'yellow');
  }
}
