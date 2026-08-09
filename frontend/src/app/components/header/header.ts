import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {

  protected authService = inject(AuthService);
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
