import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'enabledPipe'})
export class EnablePipe implements PipeTransform {
  transform(value: number): any {
    switch (value) {
      case 1:
        return '正常';
      case 0:
        return '冻结';
      default:
        return '';
    }
  }

}
