import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-nav-trainer',
  standalone: true,
  imports: [],
  templateUrl: './nav-trainer.component.html',
  styleUrl: './nav-trainer.component.scss',
})
export class NavTrainerComponent {
  constructor(private authService: AuthService) {}

  logout() {
    this.authService.logout();
  }
}
