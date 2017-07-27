import {Component, OnInit} from '@angular/core';
import {HttpService} from "./core/service/http.service";
import {ClientService} from "./client/service/client.service";
import {Message} from 'primeng/primeng';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  // 消息提示
  messages: Message[] = [];

  // 标题
  title = "统一用户管理系统";

  // 用户名
  user: any;

  constructor(private httpService: HttpService,
              private clientService: ClientService) {
  }

  ngOnInit(): void {
    let user = localStorage.getItem('user');
    if(user) {
      this.user = JSON.parse(user);
    }

    /* 监听提示信息变化 */
    this.httpService.messages$.subscribe(msgs => {
      this.messages = msgs;
    });

    /* 监听client模块信息变化 */
    this.clientService.message$.subscribe(
      msg => {
        this.user = msg;
      }, error => {
        console.error('error: ' + error);
      }
    );
  }

}
