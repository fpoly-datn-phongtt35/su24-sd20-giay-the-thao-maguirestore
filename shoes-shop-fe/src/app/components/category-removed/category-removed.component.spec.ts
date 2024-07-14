import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryRemovedComponent } from './category-removed.component';

describe('CategoryRemovedComponent', () => {
  let component: CategoryRemovedComponent;
  let fixture: ComponentFixture<CategoryRemovedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategoryRemovedComponent]
    });
    fixture = TestBed.createComponent(CategoryRemovedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
