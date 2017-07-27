import {HttpService} from '../../core/service/http.service';
import {ActivatedRoute} from '@angular/router';

export abstract class BaseDetail<T> {

  operateType: string; // 操作类型
  detailData: T; // 详细数据
  display: boolean;

  constructor(protected httpService: HttpService,
              protected route: ActivatedRoute) {
  }

  /**
   * 初始化
   * @param url
   * @returns {any}
   */
  init(url: string): Promise<boolean> {
    this.operateType = this.route.snapshot.params['type'];
    // 不是新增操作，获取详细数据
    if (this.operateType !== 'add') {
      const dataId = this.route.snapshot.params['id'];
      return this.httpService
        .findById(url, dataId)
        .then(res => {
          this.detailData = res;
          return true;
        });
    } else {
      return Promise.resolve(false);
    }
  }

  /**
   * 取消
   */
  abstract goBack();

  /**
   * 保存
   */
  abstract save();

  /**
   * 取消或保存
   * @param event
   */
  doExecute(event) {
    if (event.goBack) {
      this.goBack();
    }
    if (event.save) {
      this.save();
    }
  }

  initDialog(url: string,type:string,id:any): Promise<boolean> {
    this.operateType = type;
    // 不是新增操作，获取详细数据
    if (this.operateType !== 'add') {
      const dataId = id;
      return this.httpService
        .findById(url, dataId)
        .then(res => {
          this.detailData = res;
          return true;
        });
    } else {
      return Promise.resolve(false);
    }
  }


}
