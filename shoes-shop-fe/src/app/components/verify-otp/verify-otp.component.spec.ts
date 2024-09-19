import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifyOTPComponent } from './verify-otp.component';

describe('LoginCustomerComponent', () => {
  let component: VerifyOTPComponent;
  let fixture: ComponentFixture<VerifyOTPComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerifyOTPComponent]
    });
    fixture = TestBed.createComponent(VerifyOTPComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
