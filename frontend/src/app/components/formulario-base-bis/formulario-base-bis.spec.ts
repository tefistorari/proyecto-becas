import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioBaseBis } from './formulario-base-bis';

describe('FormularioBaseBis', () => {
  let component: FormularioBaseBis;
  let fixture: ComponentFixture<FormularioBaseBis>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioBaseBis],
    }).compileComponents();

    fixture = TestBed.createComponent(FormularioBaseBis);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
