import {Pagination} from './pagination';

export abstract class SimpleBase<T> extends Pagination<T> {

  filter: any; //封装查询条件

  firstIndex: number = 0;//当前页的第一个数的序号，从0开始

  viewData: Array<T> = []; //查询到的数据

  /**
   * 从数据库获得数据列表
   * @param currentPage
   * @param rowsPerPage
   * @param filter
   */
  abstract getDataByPage(currentPage: any, rowsPerPage: any, filter: T);

  /**
   * 查询
   */
  abstract search() ;

  /**
   * 双击查看详情事件
   * @param event
   */
  abstract getDetailEvent(event);

  /**
   * 分页返回的数据处理
   * @param res
   * @returns {boolean}
   */
  setData(res): boolean {
    this.page = res;
    this.firstIndex = this.page.number * this.page.size;//第一条数据的序列数
    this.setIndex(this.firstIndex, this.page.numberOfElements);//为序号赋值
    this.viewData = this.page.content;
    return true;
  }

  /**
   * 跳转页面
   * @param event
   */
  onPageChange(event: any): void {
    this.page.number = event.page;
    this.getDataByPage(this.page.number, this.page.size, this.filter);
  }

  /**
   * 页面大小发生改变
   * @param event
   */
  onSizeChange(event: any) {
    this.page.size = event.size;
    this.getDataByPage(this.page.number, this.page.size, this.filter);
  }

}
