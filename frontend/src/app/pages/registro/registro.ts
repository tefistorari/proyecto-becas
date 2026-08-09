import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-registro',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './registro.html',
  styleUrl: './registro.css',
})
export class Registro {
  private authService = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  registerForm = this.fb.nonNullable.group({

    nombre: ['', Validators.required],
    apellido: ['', Validators.required],
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8)
      ]
    ]
  });

  registrarse(): void {

    if(this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    const request = this.registerForm.getRawValue();

    this.authService.register(request).subscribe({
      next: (response) => {
        if(response.rol === 'ADMIN') {

          this.router.navigate(['/admin/dashboard']);

        } else {

          this.router.navigate(['/alumno/dashboard']);
        }
      }, 
      error: (err) => {

        console.error("Error al registrar usuario", err);
      }
    })

  }


}
