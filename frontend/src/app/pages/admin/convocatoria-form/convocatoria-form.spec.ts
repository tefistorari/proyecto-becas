import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConvocatoriaForm } from './convocatoria-form';

describe('ConvocatoriaForm', () => {
  let component: ConvocatoriaForm;
  let fixture: ComponentFixture<ConvocatoriaForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConvocatoriaForm],
    }).compileComponents();

    fixture = TestBed.createComponent(ConvocatoriaForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
