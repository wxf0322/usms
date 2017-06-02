import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CmUnitComponent } from './cm-unit.component';

describe('CmUnitComponent', () => {
  let component: CmUnitComponent;
  let fixture: ComponentFixture<CmUnitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CmUnitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CmUnitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
