export class Institution {
  /**
   * 机构ID
   */
  id: number;
  /**
   * 中文名称
   */
  label: string;
  /**
   * 英文名称
   */
  name: string;
  /**
   * 父机构ID
   */
  parentId: number;
  /**
   * 类型 1:机构 2:部门
   */
  type: number;
  /**
   * 行政区划代码
   */
  adminDivisionCode: string;
  /**
   * 行政区划
   */
  adminDivision: string;
  /**
   * 可用状态 1:正常 0:冻结
   */
  enabled: number;
  /**
   * 社会信用统一代码
   */
  socialCreditUnicode: string;
  /**
   * 组织机构代码
   */
  orgCode: string;
  /**
   * 手动排序
   */
  manualSn: string;
}
