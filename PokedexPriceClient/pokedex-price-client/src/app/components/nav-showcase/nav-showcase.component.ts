import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-showcase',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './nav-showcase.component.html',
  styleUrl: './nav-showcase.component.scss',
})
export class NavShowcaseComponent {
  iconValue: string = 'menu';
  menuValue: boolean = false;

  constructor(private router: Router) {}

  openMenu() {
    this.menuValue = !this.menuValue;
    this.iconValue = this.menuValue ? 'close' : 'menu';
  }

  signin() {
    this.router.navigate(['/signin']);
  }
}
