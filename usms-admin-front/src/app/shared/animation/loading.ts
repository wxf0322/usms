/**
 * 配置http请求加载动画
 */
import {IBusyConfig} from "angular2-busy";
export const Loading: IBusyConfig = {
  message: 'loading...',
  backdrop: true,
  delay: 0,
  minDuration: 2000,
  template: `
     <div style="background: url('../../../assets/img/loading.gif') no-repeat center ;height: 240px; background-size: 72px;">
            <div style="margin-top: 110px;text-align: center; font-size: 18px; font-weight: 700;">
                {{message}}
            </div>
     </div>`
};
