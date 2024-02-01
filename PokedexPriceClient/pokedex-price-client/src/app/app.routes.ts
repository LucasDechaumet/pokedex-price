import { Routes } from '@angular/router';
import { SignInScreenComponent } from './screens/sign-in-screen/sign-in-screen.component';
import { ShowcaseComponent } from './screens/showcase/showcase.component';
import { NotFoundComponent } from './screens/not-found/not-found.component';
import { HomeScreenComponent } from './screens/home-screen/home-screen.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: 'showcase',
    component: ShowcaseComponent,
    title: `MONPRICER - La plateforme virtuelle d'un jeu bien réel`,
  },
  { path: 'signin', component: SignInScreenComponent, title: 'Se connecter' },
  {
    path: '',
    component: HomeScreenComponent,
    title: 'Accueil',
    canActivate: [AuthGuard],
  },
  { path: '**', component: NotFoundComponent },
];
