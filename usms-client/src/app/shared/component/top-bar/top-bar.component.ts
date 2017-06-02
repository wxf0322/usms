import {Component, EventEmitter, Input, Output} from "@angular/core"

@Component({
  moduleId: module.id,
  selector: 'eve-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})

export class TopBarComponent {
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

