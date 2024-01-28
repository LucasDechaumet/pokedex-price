import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Register } from '../../interfaces/register';

@Component({
  selector: 'app-sign-in-screen',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './sign-in-screen.component.html',
  styleUrl: './sign-in-screen.component.scss',
})
export class SignInScreenComponent {
  constructor(private authService: AuthService) {}

  onSubmit(formData: Register) {
    console.log('Données du formulaire :', formData);
    this.authService.signIn(formData);
  }
}
