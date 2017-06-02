import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CmMemberComponent } from './cm-member.component';

describe('CmMemberComponent', () => {
  let component: CmMemberComponent;
  let fixture: ComponentFixture<CmMemberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CmMemberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CmMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
