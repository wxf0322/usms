import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import {Subject} from 'rxjs/Subject';
import {Message} from 'primeng/primeng';
import 'rxjs/add/operator/toPromise';
import * as $ from 'jquery';


/**
 *  HTTP公用服务组件
 */
@Injectable()
export class HttpService {

  // 表单类型
  private formHeaders = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});

  // json类型
  private jsonHeaders = new Headers({'Content-Type': 'application/json'});

  /* 消息提示 */
  private messages = new Subject<Message[]>(); // Observable Message[] sources
  messages$ = this.messages.asObservable(); // Observable Message[] streams
  private msgs: Message[];

  constructor(private http: Http) {
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
    return this.http.post(url, obj, {headers: this.jsonHeaders})
      .toPromise()
      .then(res => res.json())
      .catch(this.handleError);
  }

  /**
   * 删除id数组的记录，后端用columns进行读取
   * @param url
   * @param objects
   * @returns {Promise<any>}
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
    url += '?id=' + id;
    return this.http.get(url)
      .toPromise()
      .then(res => res.json())
      .catch(this.handleError);
  }

  /**
   * 根据条件得到对象
   * @param url
   * @param dataMap 用户自定义的对象
   * @returns {Promise<any>}
   */
  findByParams(url: any, params?: any) {
    return this.executeByParams(url, params);
  }

  /**
   * 根据条件执行
   * @param url
   * @param params
   * @returns {Promise<any>}
   */
  executeByParams(url: any, params?: any) {
    const requestBody = (params == null) ? null : $.param(params);
    return this.http.post(url, requestBody, {headers: this.formHeaders})
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
    const pageBean = {
      page: currentPage,
      size: rowsPerPage
    };
    const pageRequest = $.extend(pageBean, queryParams);
    const requestBody = $.param(pageRequest);
    return this.http.post(url, requestBody, {headers: this.formHeaders})
      .toPromise()
      .then(res => res.json())
      .catch(this.handleError);
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

}
