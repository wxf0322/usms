export class Page<T> {
  //表对象的集合
  content: T[];
  //总共页数
  totalPages: number;
  //总共有几行
  totalElements: number;
  //是否最后一页
  last: boolean;
  //当前页数
  number: number = 0;
  //当前行数
  size: number = 10;
  //当前页数据的个数
  numberOfElements: number;
  //当前页第一个数的序号
  firstIndex: number = 0;
}
