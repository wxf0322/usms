import { Component, OnInit } from '@angular/core';
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {HttpService} from "../../../core/service/http.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-allocation',
  templateUrl: './user-allocation.component.html',
  styleUrls: ['./user-allocation.component.css']
})
export class UserAllocationComponent extends SimpleBaseDetailUtil<any> implements OnInit {

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
  }

  goBack() {
  }

  save() {
  }


  ngOnInit() {
  }

}
