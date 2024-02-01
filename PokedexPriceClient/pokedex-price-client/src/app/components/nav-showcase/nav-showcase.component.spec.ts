import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavShowcaseComponent } from './nav-showcase.component';

describe('NavShowcaseComponent', () => {
  let component: NavShowcaseComponent;
  let fixture: ComponentFixture<NavShowcaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavShowcaseComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NavShowcaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
