/**
 * 子系统全局配置文件
 */
export const ClientVariable = Object.freeze({
  authUrl: 'http://192.168.200.209:8080/usms/authorize', // 服务器认证地址
  clientId: '6433ada9-de68-40ba-89a0-7aa8ee9128df', // 本应用在单点服务器的客户端id
  responseType: 'code', // 相应类型
  //redirectUri: 'http://localhost:4200/#/login', // 登录成功后重定向地址
  redirectUri:'http://192.168.200.209:8080/uma/#/login',
  indexUri: 'usms', // 项目首页地址
});
