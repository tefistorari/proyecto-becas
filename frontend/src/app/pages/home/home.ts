import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Ubicacion } from "../../components/ubicacion/ubicacion";

@Component({
  selector: 'app-home',
  imports: [Ubicacion],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {

}
