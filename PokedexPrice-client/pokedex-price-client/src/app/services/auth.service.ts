import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { Register } from '../interfaces/register';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isLogged: boolean = localStorage.getItem('accessToken') ? true : false;
  headers = {
    'Content-Type': 'application/json',
  };

  constructor(private router: Router) {}

  signIn(formRegister: Register) {
    fetch(`${environment.apiKey}/sign/in`, {
      method: 'POST',
      body: JSON.stringify(formRegister),
      headers: this.headers,
    }).then(async (response: Response) => {
      if (response.status === 200) {
        const responseData = await response.json();
        localStorage.setItem('refreshToken', responseData.refreshToken);
        localStorage.setItem('accessToken', responseData.accessToken);
        this.router.navigate(['']);
      } else if (response.status === 500) {
        const errorData = await response.json();
        console.error('Erreur lors de la connexion :', errorData.message);
      }
    });
  }

  logout() {
    this.isLogged = false;
    localStorage.removeItem('isLogged');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('accessToken');
    this.router.navigate(['/showcase']);
  }
}
