import {Pipe, PipeTransform} from '@angular/core';
/**
 * 案（事）件分级
 */
@Pipe({name: 'caseLvlCdePipe'})
export class CaseLvlCdePipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case '1':
        return '等级一';
      case '2':
        return '等级二';
      default:
        return '';
    }
  }
}

/**
 * 案（事）件类型
 */
@Pipe({name: 'caseTypePipe'})
export class CaseTypePipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case '1':
        return '类型一';
      case '2':
        return '类型二';
      default:
        return '';
    }
  }
}

/**
 * 案件状态
 */
@Pipe({name: 'seriousCaseStatusPipe'})
export class SeriousCaseStatusPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case '1':
        return '状态一';
      case '2':
        return '状态二';
      default:
        return '';
    }
  }
}

