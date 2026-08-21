import { Component } from '@angular/core';
import { Ubicacion } from "../../../../components/ubicacion/ubicacion";

@Component({
  selector: 'app-formulario-base',
  imports: [Ubicacion],
  templateUrl: './formulario-base.html',
  styleUrl: './formulario-base.css',
})
export class FormularioBase {}
