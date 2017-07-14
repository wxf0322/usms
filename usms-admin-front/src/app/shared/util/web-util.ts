

export class WebUtil {

  /**
   * 获取url参数值
   * @param name
   * @returns {any}
   */
  static getQueryString(name): string {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]);
    return '';
  }

  /**
   * 删除参数值
   * @param url
   * @param ref
   * @returns {any}
   */
  static delQueryString(url, ref) {
    let str = "";
    if (url.indexOf('?') != -1) {
      str = url.substr(url.indexOf('?') + 1);
    } else {
      return url;
    }
    let returnUrl = "";
    if (str.indexOf('&') != -1) {
      let arr = str.split('&');
      for (let i in arr) {
        if (arr[i].split('=')[0] != ref) {
          returnUrl = returnUrl + arr[i].split('=')[0] + "=" + arr[i].split('=')[1] + "&";
        }
      }
      return url.substr(0, url.indexOf('?')) + "?" + returnUrl.substr(0, returnUrl.length - 1);
    } else {
      let arr = str.split('=');
      if (arr[0] == ref) {
        return url.substr(0, url.indexOf('?'));
      } else {
        return url;
      }
    }
  }

}
