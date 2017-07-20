import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import {Subject} from 'rxjs/Subject';
import {Message} from 'primeng/primeng';
import {Loading} from "../../shared/animation/loading";
import {IBusyConfig} from "tixif-ngx-busy";
import 'rxjs/add/operator/toPromise';
import * as $ from 'jquery';

/**
 *  HTTP公用服务组件
 */
@Injectable()
export class HttpService {

  /**
   * 表单类型请求
   * @type {Headers}
   */
  private formHeaders = new Headers({
    'Cache-Control': 'no-cache',
    'Pragma': 'no-cache',
    'Expires': 'Sat, 01 Jan 2000 00:00:00 GMT',
    'Content-Type': 'application/x-www-form-urlencoded'
  });

  /**
   * json类型请求
   * @type {Headers}
   */
  private jsonHeaders = new Headers({
    'Cache-Control': 'no-cache',
    'Pragma': 'no-cache',
    'Expires': 'Sat, 01 Jan 2000 00:00:00 GMT',
    'Content-Type': 'application/json'
  });

  /**
   * 消息提示
   * @type {Subject<Message[]>}
   */
  private messages = new Subject<Message[]>();

  /**
   * 消息监听器
   */
  messages$ = this.messages.asObservable();

  /**
   * 消息显示
   */
  private msgs: Message[];

  /**
   * loading
   */
  loading: IBusyConfig = Loading;

  constructor(private http: Http) {
  }

  /**
   * 错误响应
   * @param error
   * @returns {Promise<never>}
   */
  private handleError(error: any): Promise<any> {
    console.error('出错了：', error);
    console.log('状态：', error.status);
    return Promise.reject(error.message || error);
  }

  /**
   * 设置消息提示信息
   * @param msg
   */
  setMessage(msg) {
    this.msgs = [];
    this.msgs.push(msg);
    this.messages.next(this.msgs);
  }

  /**
   * 更新数据（包括新增、编辑）
   * @param url
   * @param obj
   * @returns {Promise<any>}
   */
  saveOrUpdate(url: string, obj: any) {
    return this.loading.busy = this.http.post(url, obj, {headers: this.jsonHeaders})
      .toPromise()
      .then(res => res.json())
      .catch(this.handleError);
  }

  /**
   * 删除id数组的记录，后端用columns进行读取
   * @param url
   * @param cols
   * @returns {Promise<any|TResult2|TResult1>}
   */
  deleteByColNames(url: string, cols: string[]) {
    const requestBody = $.param({
      columns: cols.join(',')
    });
    return this.http.post(url, requestBody, {headers: this.formHeaders})
      .toPromise()
      .then(res => res.json())
      .catch(this.handleError);
  }

  /**
   * 带附件的更新
   * @param url
   * @param data 要更新的实体
   * @param files 新上传的附件
   * @param deleteFileIds 要删除附件ID
   * @returns {Promise<T>}
   */
  updateWithUpload(url: any, data: any, files: any, deleteFileIds?: any) {
    return new Promise((resolve, reject) => {
      const xhr: XMLHttpRequest = new XMLHttpRequest();
      const formData = new FormData();

      if (files != null) {
        for (let i = 0; i < files.length; i++) {
          formData.append('uploadFile', files[i]);
        }
      }
      if (deleteFileIds) {
        formData.append('deleteFiles', deleteFileIds);
      }
      formData.append('jsonData', JSON.stringify(data));
      xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            resolve(<any>JSON.parse(xhr.response));
          } else {
            reject(xhr.response);
          }
        }
      };
      xhr.open('POST', url, true);
      xhr.send(formData);
    });
  }

  /**
   * 根据id得到对象
   * @param url
   * @param id
   * @returns {any}
   */
  findById(url: string, id: string) {
    let params = {id: id};
    return this.loading.busy = this.findByParams(url, params);
  }

  /**
   * 根据条件得到对象
   * @param url
   * @param params
   * @returns {Promise<any|TResult2|TResult1>}
   */
  findByParams(url: any, params?: any) {
    return this.loading.busy = this.executeByParams(url, params);
  }

  /**
   * 根据条件执行
   * @param url
   * @param params
   * @returns {Promise<any>}
   */
  executeByParams(url: any, params?: any) {
    url = encodeURI(url);
    const requestBody = (params == null) ? null : $.param(params);
    return this.loading.busy = this.http.post(url, requestBody, {headers: this.formHeaders})
      .toPromise()
      .then(res => res.json())
      .catch(this.handleError);
  }

  /**
   * 分页查询
   * @param url
   * @param currentPage 目标页
   * @param rowsPerPage 目标数据数量
   * @param queryParams 查询条件
   * @returns {Promise<any>}
   */
  findByPage(url: string, currentPage: number, rowsPerPage: number, queryParams: any) {
    url = encodeURI(url);
    const pageBean = {
      page: currentPage,
      size: rowsPerPage
    };
    const pageRequest = $.extend(pageBean, queryParams);
    const requestBody = $.param(pageRequest);
    return this.loading.busy = this.http.post(url, requestBody, {headers: this.formHeaders})
      .toPromise()
      .then(res => res.json())
      .catch(this.handleError);
  }

}
