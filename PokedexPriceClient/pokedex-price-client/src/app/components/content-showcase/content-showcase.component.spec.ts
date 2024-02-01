import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentShowcaseComponent } from './content-showcase.component';

describe('ContentShowcaseComponent', () => {
  let component: ContentShowcaseComponent;
  let fixture: ComponentFixture<ContentShowcaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContentShowcaseComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ContentShowcaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
