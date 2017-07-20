import {Component, EventEmitter, Input, Output} from "@angular/core"

@Component({
  selector: 'eve-dialog-bar',
  templateUrl: './dialog-bar.component.html',
  styleUrls: ['./dialog-bar.component.css']
})

export class DialogBarComponent {
  @Input() title = '';

  @Input() display = false;//保存按钮是否可点击

  @Output() execute = new EventEmitter();

  goBack() {
    this.execute.emit({goBack: true, save: false});
  }

  save() {
    this.execute.emit({goBack: false, save: true});
  }

}

