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
  selectedFiles: Array<any> = [];
  selection: TreeNode[];

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
  @Input() propagateSelectionUp: boolean;
  @Input() propagateSelectionDown: boolean;

  @Output() selectedEvent = new EventEmitter();

  onMouseclick(e) {
    if (this.disabled || this.readonly) { // 禁用和只读，点击无效果
      return;
    }
    this.selfClick = true;

    //点击树结点则隐藏
    if (this.treeItemClick) {
      this.panelVisible = false;
      this.treeItemClick=false;
    } else {
      this.panelVisible = true;
    }
  }

  hide() {
    this.treeItemClick = true;
  }

  //添加选择项
  nodeSelect(event) {
    if (this.selectionMode === 'single') {
      this.selectedFiles = [];
    }

    let temp = {
      gridId: event.node.gridId,
      gridName: event.node.label
    };
    this.selectedFiles.push(temp);

    this.showResult();

    //发送
    this.selectedEvent.emit(this.selectedFiles);
  }

  //移除选择项
  nodeUnselect(event) {

    let temp = {
      gridId: event.node.gridId,
      gridName: event.node.label
    };
    this.selectedFiles.splice(this.selectedFiles.indexOf(temp), 1);

    this.showResult();
  }

  //显示选择结果
  showResult() {
    this.selectedNames = '';

    if (this.selectionMode === 'single') {
      if (this.selectedFiles.length > 0) {
        this.selectedNames = this.selectedFiles[0].gridName;
      }
    } else {
      this.selectedFiles.forEach(temp => {
        this.selectedNames += temp.gridName + ';'
      });
    }
    this.hide();
  }
}
