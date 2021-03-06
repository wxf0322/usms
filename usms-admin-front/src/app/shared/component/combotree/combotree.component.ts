import {Component, EventEmitter, Input, OnInit, Output, Renderer, ViewEncapsulation} from '@angular/core';
import {TreeNode} from 'primeng/primeng';

@Component({
  selector: 'eve-combotree',
  templateUrl: './combotree.component.html',
  styleUrls: ['./combotree.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CombotreeComponent implements OnInit {

  disabled: boolean; // 是否禁用

  readonly: boolean; // 是否只读

  selfClick = false; // 标记点击是组件自身，而不是其他区域

  panelVisible: boolean; // 是否显示下拉面板

  triangleClass: string; // 三角形

  // 输入框大小
  @Input() size: number;

  // 选中的节点名称
  @Input() selectedNames: string; // 被选择的数据名称

  documentClickListener: any; // 监听

  // 树形节点
  @Input() value: TreeNode[]; // 树形节点

  // 双向绑定 selection
  selectionValue: TreeNode[]; // 选中的节点

  @Output() selectionChange = new EventEmitter();

  @Input()
  get selection() {
    return this.selectionValue;
  }

  set selection(value) {
    this.selectionValue = value;
    this.selectedNames = this.selectionValue.map(node => node.label).join(',');
    this.selectionChange.emit(this.selectionValue);
  }

  constructor(private renderer: Renderer) {
  }

  ngOnInit() {
    this.triangleClass = 'fa fa-caret-up';
    // 监听body的点击事件
    this.documentClickListener = this.renderer.listenGlobal('body', 'click', (event) => {
      if (event.target.id !== 'combotree' && this.selfClick === false) {
        this.triangleClass = 'fa fa-caret-up';
        this.panelVisible = false;
      }
      this.selfClick = false;
    });
  }


  onMouseclick(event) {
    // 如果禁用和只读，点击无效果
    if (this.disabled || this.readonly) {
      return;
    }
    this.triangleClass = 'fa fa-caret-down';
    this.selfClick = true;
    this.panelVisible = true;
  }

  // 添加选择项
  nodeSelect(event) {
    this.showResult();
  }

  nodeUnSelect(event) {
    this.showResult();
  }

  showResult() {
    this.selectedNames = this.selectionValue.map(node => node.label).join(',');
  }

}
