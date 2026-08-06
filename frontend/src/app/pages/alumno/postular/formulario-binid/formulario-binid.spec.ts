import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioBinid } from './formulario-binid';

describe('FormularioBinid', () => {
  let component: FormularioBinid;
  let fixture: ComponentFixture<FormularioBinid>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioBinid],
    }).compileComponents();

    fixture = TestBed.createComponent(FormularioBinid);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
