import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComputerDetailsComponent } from './computer-details.component';

describe('ComputerDetailsComponent', () => {
  let component: ComputerDetailsComponent;
  let fixture: ComponentFixture<ComputerDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ComputerDetailsComponent]
    });
    fixture = TestBed.createComponent(ComputerDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
