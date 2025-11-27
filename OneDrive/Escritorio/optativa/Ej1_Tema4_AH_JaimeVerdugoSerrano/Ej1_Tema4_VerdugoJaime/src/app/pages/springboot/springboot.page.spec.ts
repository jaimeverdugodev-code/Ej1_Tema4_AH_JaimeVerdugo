import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SpringbootPage } from './springboot.page';

describe('SpringbootPage', () => {
  let component: SpringbootPage;
  let fixture: ComponentFixture<SpringbootPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(SpringbootPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
