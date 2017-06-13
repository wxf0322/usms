import {Pipe, PipeTransform} from '@angular/core';
/**
 * 案（事）件分级
 */
@Pipe({name: 'isOrNotPipe'})
export class IsOrNotPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case '1':
        return '是';
      case '0':
        return '否';
      default:
        return '';
    }
  }
}

