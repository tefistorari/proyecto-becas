import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { required } from '@angular/forms/signals';
import { passwordsMatchValidator } from '../../validators/auth.validator';

@Component({
  selector: 'app-cambio-password',
  imports: [ReactiveFormsModule],
  templateUrl: './cambio-password.html',
  styleUrl: './cambio-password.css',
})
export class CambioPassword {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  cambiando = signal(false);
  error = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    passwordActual: ['', Validators.required],
    passwordNueva: ['', [Validators.required, Validators.minLength(8)]],

    confirmarPassword: ['', Validators.required]
  }, {validators: passwordsMatchValidator()});

  cambiarPassword(): void {

    this.error.set(null);

    if(this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.cambiando.set(true);

    const {
      passwordActual,
      passwordNueva
    } = this.form.getRawValue();

    this.authService.changePassword({
      passwordActual,
      passwordNueva }).subscribe({
      next: () => {
        this.cambiando.set(false);

        this.authService.logout();
        this.router.navigate(['/']);
      },

      error: (err) => {
        this.cambiando.set(false);

        this.error.set(
          err.error?.message ??
            'No se pudo cambiar la contraseña.'
        );
      }
    });
  }

  invalido(controlName: string): boolean {
    const control = this.form.get(controlName);

    return !!(
      control &&
      control.invalid &&
      control.touched
    );
  }

  mensajeError(controlName: string): string {

    const control = this.form.get(controlName);

    if(!control?.errors){
      return '';
    }

    if(control.hasError('required')) {
      return 'La contraseña es obligatoria.';
    }

    if(control.hasError('minlength')) {
      return 'La contraseña debe tener al menos 8 caracteres.';
    }

    return 'Valor invalido.';
  }

  volver(): void {
    if(this.authService.isAdmin()) {
      this.router.navigate(['/admin/dashboard']);
    } else {
      this.router.navigate(['/alumno/dashboard']);
    }
  }
}
