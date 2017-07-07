import {HttpService} from '../../core/service/http.service';
import {ConfirmationService, MenuItem} from 'primeng/primeng';
import {ActivatedRoute, Router} from '@angular/router';
import {BasePaginator} from '../filter/base-paginator';
import {Renderer} from '@angular/core';

export abstract class BaseTable<T> extends BasePaginator<T> {

  isDropPanelShow = false; // 显示高级查询面板

  selfClick: boolean; // 标记点击是组件自身，而不是其他区域

  btnItems: MenuItem[]; // split按钮

  items: MenuItem[]; // 右键操作

  selectedData: Array<T> = []; // 被选中的数据集合

  documentClickListener: any; // 监听事件

  constructor(protected router: Router,
              protected route: ActivatedRoute,
              protected httpService: HttpService,
              protected confirmationService: ConfirmationService,
              protected renderer: Renderer) {
    super();

    // 设置split按钮
    this.btnItems = [
      {label: '删除', icon: 'fa-remove', command: () => this.deleteSelected()}
    ];

    // 监听body的点击事件
    this.documentClickListener = this.renderer.listenGlobal('body', 'click', (event) => {
      if (event.target.id !== 'advancedQuery' && this.selfClick === false) {
        this.isDropPanelShow = false;
      }
      this.selfClick = false;
    });
  }

  /**
   * 跳转到新增、编辑页面
   * @param type
   * @param id
   */
  gotoDetail(type: string, id: string) {
    this.router.navigate(['detail', {type: type, id: id}], {relativeTo: this.route});
  }


  /**
   * 双击查看详情事件
   * @param event
   */
  getDetailEvent(event) {
    this.gotoDetail('edit', event.data.id);
  }

  /**
   * 查询
   */
  search() {
    this.getDataByPage(0, this.page.size, this.filter);
    this.isDropPanelShow = false;
    this.selfClick = false;
  }

  /**
   * 高级查询切换
   */
  togglePanel() {
    this.isDropPanelShow = !this.isDropPanelShow;
  }

  /**
   * 删除选择的数据
   */
  abstract deleteSelected();

  /**
   * 页面弹框的显示效果
   */
  abstract showDialog(type: string, id: string);

  /**
   * 页面的查询方法
   */
  abstract query();

  /**
   * 删除
   * @param url
   */
  delete(url: string, colName: string) {
    this.confirmationService.confirm({
      message: '确定要删除这' + this.selectedData.length + '条数据吗？',
      header: '删除',
      icon: 'fa fa-question-circle',
      accept: () => {
        const colNames = this.selectedData.map(data => data[colName]);
        this.httpService.deleteByColNames(url, colNames)
          .then(res => {
            // 刷新页面列表
            if (this.page.numberOfElements <= this.selectedData.length && this.page.number > 0) {
              this.page.number--;
            }
            // 消息提示
            this.httpService.setMessage({
              severity: 'success',
              summary: '操作成功',
              detail: '成功删除' + this.selectedData.length + '条数据'
            });
            // 删除成功，清空选中数据
            this.selectedData = [];
            this.getDataByPage(this.page.number, this.page.size, this.filter);
          });
      },
      reject: () => {
      }
    });
  }

  /**
   * 警告
   * @param msg
   */
  warning(msg) {
    this.confirmationService.confirm({
      message: msg,
      header: '警告',
      icon: 'fa fa-question-circle',
      accept: () => {
      },
      reject: () => {
      }
    });
  }

  /**
   * 点击高级查询部分
   */
  selfClickEvent() {
    this.selfClick = true;
  }

  /**
   * 查询条件转换格式，value值多个用，隔开
   * @param data
   * @returns {any}
   */
  queryParamsToString(data: Array<any>): string {
    if (data && data.length > 0) {
      let str = '';
      data.forEach(temp => {
        str += temp + ',';
      });
      return str.substring(0, str.length - 1);
    } else {
      return '';
    }
  }

}
