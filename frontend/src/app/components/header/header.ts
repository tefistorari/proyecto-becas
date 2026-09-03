import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { LogoutButton } from "../logout-button/logout-button";

@Component({
  selector: 'app-header',
  imports: [RouterLink, LogoutButton],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  protected authService = inject(AuthService);
  
}
