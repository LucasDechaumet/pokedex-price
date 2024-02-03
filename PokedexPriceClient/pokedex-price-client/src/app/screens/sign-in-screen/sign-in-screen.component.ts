import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Register } from '../../interfaces/register';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-sign-in-screen',
  standalone: true,
  imports: [FormsModule, MatIconModule],
  templateUrl: './sign-in-screen.component.html',
  styleUrl: './sign-in-screen.component.scss',
})
export class SignInScreenComponent {
  iconVisibility: string = 'visibility_off';
  visible: boolean = false;

  constructor(private authService: AuthService, private location: Location) {}

  onSubmit(formData: Register) {
    console.log('Données du formulaire :', formData);
    this.authService.signIn(formData);
  }

  viewPassword() {
    this.iconVisibility = this.visible ? 'visibility_off' : 'visibility';
    this.visible = !this.visible;
  }

  close() {
    this.location.back();
  }
}
