import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { NavTrainerComponent } from '../../components/nav-trainer/nav-trainer.component';

@Component({
  selector: 'app-home-screen',
  standalone: true,
  imports: [NavTrainerComponent],
  templateUrl: './home-screen.component.html',
  styleUrl: './home-screen.component.scss',
})
export class HomeScreenComponent {
  constructor(private authService: AuthService) {}

  logout() {
    this.authService.logout();
  }
}
