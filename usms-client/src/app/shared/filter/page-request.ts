/*分页请求*/
import {Condition} from "./condition";
import {Sort} from "./sort";
export class PageRequest {
  public currentPage: number;
  public rowsPerPage: number;

  public conditionFilters: Condition[];
  public sortFilters: Sort[];
}
