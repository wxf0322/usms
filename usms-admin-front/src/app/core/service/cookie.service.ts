import {Injectable} from '@angular/core';
/**
 * cookie服务
 * @author Warren Chan 2017年5月26日 10:38:33
 */
@Injectable()
export class CookieService {

  constructor() {
  }

  /**
   * 检查cookie是否存在
   * @param name
   * @returns {boolean}
   */
  public check(name: string): boolean {
    if (typeof document === 'undefined') {
      return false;
    }
    name = encodeURIComponent(name);
    const regexp = new RegExp('(?:^' + name + '|;\\s*' + name + ')=(.*?)(?:;|$)', 'g');
    const exists = regexp.test(document.cookie);
    return exists;
  }

  /**
   * 通过cookie名字获取cookie的value值
   * @param name cookie名字
   * 其他说明：result除了结果数组（第一个数组为匹配的值），
   * 还包括index属性（匹配字符串所在位置）和input属性（返回参与匹配的字符串）
   */
  public get(name: string) {
    if (this.check(name)) {
      name = encodeURIComponent(name);
      const regexp = new RegExp('(?:^' + name + '|;\\s*' + name + ')=(.*?)(?:;|$)', 'g');
      const result = regexp.exec(document.cookie);
      return decodeURIComponent(result[1]);
    } else {
      return '';
    }
  }

  /**
   * 返回所有cookie
   * @returns {any}
   */
  public getAll(): any {
    const cookies: any = {};
    if (document.cookie && document.cookie !== '') {
      const split = document.cookie.split(';');
      for (const s of split) {
        const currCookie = s.split('=');
        currCookie[0] = currCookie[0].replace(/^ /, '');
        cookies[decodeURIComponent(currCookie[0])] = decodeURIComponent(currCookie[1]);
      }
    }
    return cookies;
  }

  /**
   * 设置cookie
   * @param name cookie名字
   * @param value cookie值
   * @param expires cookie过期时间（天或者具体时间）
   * @param path
   */
  public set(name: string, value: string, expires?: number | Date,
             path?: string, domain?: string, secure?: boolean) {
    let cookieStr = encodeURIComponent(name) + '=' + encodeURIComponent(value) + ';';
    if (expires) {
      if (typeof expires === 'number') { // 数字类型转换为天
        const dtExpires = new Date(new Date().getTime() + expires * 1000 * 60 * 60 * 24);
        cookieStr += 'expires=' + dtExpires.toUTCString() + ';';
      } else { // 直接设置具体时间
        cookieStr += 'expires=' + expires.toUTCString() + ';';
      }
    }
    if (path) {
      cookieStr += 'path=' + path + ';';
    }
    if (domain) {
      cookieStr += 'domain=' + domain + ';';
    }
    if (secure) {
      cookieStr += 'secure;';
    }
    document.cookie = cookieStr;
  }

  /**
   * 删除单个cookie
   * @param name
   * @param path
   * @param domain
   */
  public delete(name: string, path?: string, domain?: string): void {
    this.set(name, '', -1, path, domain);
  }

  /**
   * 删除所有cookie
   * @param path
   * @param domain
   */
  public deleteAll(path?: string, domain?: string): void {
    const cookies: any = this.getAll();
    for (const cookieName of Object.keys(cookies)) {
      this.delete(cookieName, path, domain);
    }
  }

}
