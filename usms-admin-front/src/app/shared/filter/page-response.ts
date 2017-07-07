export class PageResponse {
  /**
   * 是否首页
   */
  first?: boolean;
  /**
   * 是否最后一页
   */
  last?: boolean;
  /**
   * 当前页页码
   */
  number?: number = 0;
  /**
   * 当前页显示的元素数量
   */
  numberOfElements?: number;
  /**
   * 每页显示元素数量
   */
  size?: number = 10;
  /**
   * 内容
   */
  content?: any;
  /**
   * 总的元素数量
   */
  totalElements?: number;
  /**
   * 总的页数
   */
  totalPages?: number;
  /**
   * 每页行数选项
   */
  rowsPerPageOptions?: number[] = [10, 15, 20];

}
