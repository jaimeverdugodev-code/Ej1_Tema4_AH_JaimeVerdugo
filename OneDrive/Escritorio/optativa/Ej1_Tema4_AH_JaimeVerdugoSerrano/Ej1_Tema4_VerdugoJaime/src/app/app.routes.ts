import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'home',
    loadComponent: () => import('./home/home.page').then((m) => m.HomePage),
  },
  {
    path: '',
    redirectTo: 'earthquake',
    pathMatch: 'full',
  },
  {
    path: 'earthquake',
    loadComponent: () => import('./pages/earthquake/earthquake.page').then( m => m.EarthquakePage)
  },
  {
    path: 'springboot',
    loadComponent: () => import('./pages/springboot/springboot.page').then( m => m.SpringbootPage)
  },
];
