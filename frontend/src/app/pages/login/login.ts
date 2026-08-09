import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Rol } from '../../models/rol';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private authService = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  loginForm = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  ingresar(): void {

    if(this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const request = this.loginForm.getRawValue();
    this.authService.login(request).subscribe({
      next: (response) => {
        
        if(response.rol === 'ADMIN' ) {
          this.router.navigate(['/admin/dashboard']);
        }
        else{
          this.router.navigate(['/alumno/dashboard']);
        }

      },
      error: (err) => {

        console.error("Error al iniciar sesión", err);

      }
    })
    
  }
}
