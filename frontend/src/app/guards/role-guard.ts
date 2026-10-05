import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth-service';
import { Rol } from '../models/rol';

export const roleGuard = (rol: Rol): CanActivateFn => {
  return () => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (authService.hasRole(rol)) {
      return true;
    }

    router.navigate(['/']);
    return false;
  };
};
