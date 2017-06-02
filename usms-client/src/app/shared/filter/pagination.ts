/**
 * 分页
 */
import {Page} from './page';

export abstract class Pagination<T> {

  //------分页
  page: Page<T> = new Page<T>();
  //首列序号的值
  index: Array<number> = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15];

  /**
   * 为首列的序列值index赋值
   */
  setIndex(firstIndex: number,numberOfElements:number) {
    this.index = [];
    for (let i = firstIndex + 1; i <= (firstIndex + numberOfElements); i++) {
      this.index.push(i);
    }
  }

}
