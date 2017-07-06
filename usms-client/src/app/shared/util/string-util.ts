export class StringUtil {

  static ltrim(s) {
    return s.replace(/(^\s*)/g, '');
  }

  static rtrim(s) {
    return s.replace(/(\s*$)/g, '');
  }

  static trim(s) {
    return s.replace(/(^\s*)|(\s*$)/g, '');
  }
}
