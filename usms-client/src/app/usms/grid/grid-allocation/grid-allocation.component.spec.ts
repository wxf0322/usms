import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GridAllocationComponent } from './grid-allocation.component';

describe('GridAllocationComponent', () => {
  let component: GridAllocationComponent;
  let fixture: ComponentFixture<GridAllocationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GridAllocationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GridAllocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
