import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CmCenterDetailComponent } from './cm-center-detail.component';

describe('CmCenterDetailComponent', () => {
  let component: CmCenterDetailComponent;
  let fixture: ComponentFixture<CmCenterDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CmCenterDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CmCenterDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
