import { Component } from '@angular/core';
import { NavShowcaseComponent } from '../../components/nav-showcase/nav-showcase.component';
import { ContentShowcaseComponent } from '../../components/content-showcase/content-showcase.component';

@Component({
  selector: 'app-showcase',
  standalone: true,
  imports: [NavShowcaseComponent, ContentShowcaseComponent],
  templateUrl: './showcase.component.html',
  styleUrl: './showcase.component.scss',
})
export class ShowcaseComponent {}
