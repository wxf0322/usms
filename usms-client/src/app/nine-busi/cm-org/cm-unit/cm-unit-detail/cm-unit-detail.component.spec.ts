import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CmUnitDetailComponent } from './cm-unit-detail.component';

describe('CmUnitDetailComponent', () => {
  let component: CmUnitDetailComponent;
  let fixture: ComponentFixture<CmUnitDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CmUnitDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CmUnitDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
