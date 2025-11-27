import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EarthquakePage } from './earthquake.page';

describe('EarthquakePage', () => {
  let component: EarthquakePage;
  let fixture: ComponentFixture<EarthquakePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(EarthquakePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
