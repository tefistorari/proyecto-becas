import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout-button',
  imports: [],
  templateUrl: './logout-button.html',
  styleUrl: './logout-button.css',
})
export class LogoutButton {
  private authService = inject(AuthService);
  private router = inject(Router);

  showLogoutModal = signal(false);

  confirmLogout(): void {
    this.showLogoutModal.set(true);
  }

  cancelLogout(): void {
    this.showLogoutModal.set(false);
  }

  logout(): void {
    this.showLogoutModal.set(false);
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
