import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormCarreraInvestigador } from './form-carrera-investigador';

describe('FormCarreraInvestigador', () => {
  let component: FormCarreraInvestigador;
  let fixture: ComponentFixture<FormCarreraInvestigador>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormCarreraInvestigador],
    }).compileComponents();

    fixture = TestBed.createComponent(FormCarreraInvestigador);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
