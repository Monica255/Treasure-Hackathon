import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeftoversComponent } from './leftovers.component';

describe('LeftoversComponent', () => {
  let component: LeftoversComponent;
  let fixture: ComponentFixture<LeftoversComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeftoversComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeftoversComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
