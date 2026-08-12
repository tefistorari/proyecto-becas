import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { passwordsMatchValidator } from '../../validators/auth.validator';

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

  errorMessage = signal('');

  registerForm = this.fb.nonNullable.group({

    nombre: ['', Validators.required],
    apellido: ['', Validators.required],
    email: ['',[Validators.required, Validators.email]],
    password: ['',[Validators.required,Validators.minLength(8)]],
    confirmarPassword: ['', Validators.required]
  }, {validators: passwordsMatchValidator()});

  registrarse(): void {

    this.errorMessage.set('');

    if(this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    // el backend no espera "confirmarPassword", así que lo sacamos antes de mandar
    const {confirmarPassword, ...request } = this.registerForm.getRawValue();

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

        this.errorMessage.set(
          err.error?.error ?? 
          'Ocurrió un error al registrarse.'
        );
      }
      
    });

  }


}
