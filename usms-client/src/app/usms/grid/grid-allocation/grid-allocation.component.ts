import { Component, OnInit } from '@angular/core';
import {SimpleBaseDetailUtil} from "../../../shared/util/simple-base-detail.util";
import {ActivatedRoute} from "@angular/router";
import {HttpService} from "../../../core/service/http.service";

@Component({
  selector: 'app-grid-allocation',
  templateUrl: './grid-allocation.component.html',
  styleUrls: ['./grid-allocation.component.css']
})
export class GridAllocationComponent extends SimpleBaseDetailUtil<any> implements OnInit {

  constructor(private location: Location,
              protected httpService: HttpService,
              protected route: ActivatedRoute) {
    super(httpService, route);
  }

  goBack() {
    // a
  }

  save() {
  }

  ngOnInit() {
  }

}
