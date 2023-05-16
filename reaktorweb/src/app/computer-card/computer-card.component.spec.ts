import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ComputerCardComponent } from './computer-card.component';

describe('ComputerCardComponent', () => {
  let component: ComputerCardComponent;
  let fixture: ComponentFixture<ComputerCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComputerCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComputerCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display the correct malware count', () => {
    component.malwareCount = 5;
    fixture.detectChanges();
    const malwareCountEl = fixture.nativeElement.querySelector('.malware-count');
    expect(malwareCountEl.textContent).toContain('5');
  });

  // Add more tests here as needed
});
