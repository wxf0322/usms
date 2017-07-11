import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'institutionTypePipe'})
export class InstitutionPipe implements PipeTransform {
  transform(value: number): any {
    switch (value) {
      case 1:
        return '机构';
      case 2:
        return '部门';
      default:
        return '';
    }
  }
}
