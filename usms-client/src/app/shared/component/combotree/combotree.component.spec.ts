import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CombotreeComponent} from './combotree.component';

describe('CombotreeComponent', () => {
  let component: CombotreeComponent;
  let fixture: ComponentFixture<CombotreeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CombotreeComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CombotreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
