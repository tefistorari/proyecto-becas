import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioBis } from './formulario-bis';

describe('FormularioBis', () => {
  let component: FormularioBis;
  let fixture: ComponentFixture<FormularioBis>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioBis],
    }).compileComponents();

    fixture = TestBed.createComponent(FormularioBis);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
