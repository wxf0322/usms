import {Component, EventEmitter, Input, OnInit, Output, Renderer} from '@angular/core';
import {TreeNode} from "primeng/primeng";

@Component({
  selector: 'eve-combotree',
  templateUrl: './combotree.component.html',
  styleUrls: ['./combotree.component.css']
})
export class CombotreeComponent implements OnInit {
  disabled: boolean; // 是否禁用
  readonly: boolean; // 是否只读
  selfClick: boolean = false; // 标记点击是组件自身，而不是其他区域
  treeItemClick: boolean = false; // 是否点击树
  panelVisible: boolean; // 是否显示下拉面板

  selectedNames: string;//被选择的数据名称


  documentClickListener: any;//监听

  ngOnInit() {
  }

  constructor(private renderer: Renderer) {
    //监听body的点击事件
    this.documentClickListener = this.renderer.listenGlobal('body', 'click', (event) => {
      if (event.target.id !== 'combotreeContain' && this.selfClick == false) {
        this.panelVisible = false;
      }
      this.selfClick = false;
    });
  }

  @Input() data: TreeNode[];
  @Input() selectionMode: string = 'single';
  @Input() selectedNodes: TreeNode[];
  @Output() selectedEvent = new EventEmitter();

  onMouseclick(e) {
    if (this.disabled || this.readonly) { // 禁用和只读，点击无效果
      return;
    }
    this.selfClick = true;

    //点击树结点则隐藏
    if (this.treeItemClick) {
      this.treeItemClick = false;
    } else {
      this.panelVisible = true;
    }
  }

  hide() {
    this.treeItemClick = true;
  }

  //添加选择项
  nodeSelect(event) {
    this.showResult();
    //发送
    this.selectedEvent.emit(this.selectedNodes);
  }


  //显示选择结果
  showResult() {
    this.selectedNames = '';
    this.hide();
  }

}
