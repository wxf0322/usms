
export class BeanUtil {
  /**
   * 拷贝对象
    * @param obj
   * @returns {any}
   */
  public static deepCopy(obj): any {
    return JSON.parse(JSON.stringify(obj));
  }

  /**
   * 拷贝数组
   * @param obj
   * @returns {any[]}
   */
  public static arrayDeepCopy(obj): any[] {
    return [].concat(JSON.parse(JSON.stringify(obj)));
  }

}
