import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ConvocatoriaService } from '../../../services/convocatoria-service';
import { BecaService } from '../../../services/beca-service';
import { Router } from '@angular/router';
import { BecaResponse } from '../../../models/beca-response';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-convocatorias',
  imports: [],
  templateUrl: './convocatorias.html',
  styleUrl: './convocatorias.css',
})
export class Convocatorias {
  
}
