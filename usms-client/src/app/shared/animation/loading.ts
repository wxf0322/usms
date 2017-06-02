/**
 * 配置http请求加载动画
 */
import {IBusyConfig} from "angular2-busy";
export const Loading: IBusyConfig = {
        message: 'Loading...',
        backdrop: true,
        delay: 200,
        minDuration: 0,
        template: `
     <div style="background: url('../../../assets/img/du.gif') no-repeat center 20px;height: 120px; background-size: 72px;">
            <div style="margin-top: 110px; text-align: center; font-size: 18px; font-weight: 700;">
                {{message}}
            </div>
     </div>
`
};
