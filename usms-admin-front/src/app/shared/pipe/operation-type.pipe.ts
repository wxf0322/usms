import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'operationTypePipe'})
export class OperationTypePipe implements PipeTransform {
  transform(value: number): any {
    switch (value) {
      case 1:
        return '按钮';
      case 2:
        return '菜单';
      case 3:
        return '系统';
      default:
        return '';
    }
  }
}
