import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-content-showcase',
  standalone: true,
  imports: [],
  templateUrl: './content-showcase.component.html',
  styleUrl: './content-showcase.component.scss',
})
export class ContentShowcaseComponent {
  constructor(private router: Router) {}

  signup() {
    this.router.navigate(['signup']);
  }
}
