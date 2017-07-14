/**
 * 全局配置文件
 */
export const GlobalVariable = Object.freeze({
  /* 基础URL地址 */
  baseUrl: 'http://localhost:8080/',

  /* oauth2 认证 */
  loginUrl: 'http://192.168.200.209:8080/usms/authorize', // 地址
  clientId: '0277956b-9520-48e5-adc1-2bbd62019bf0', // 本应用在单点服务器的客户id（对应关系）
  responseType: 'code', // 单点登录服务器定义
  redirectUri: encodeURIComponent('http://localhost:4200/#/login'), // 登录成功后重定向地址
  indexUri: 'usms'

});
