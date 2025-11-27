import { Component } from '@angular/core';
import { IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/angular/standalone';
import { EarthquakePage } from '../pages/earthquake/earthquake.page';
import { SpringbootPage } from '../pages/springboot/springboot.page';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: true,
  imports: [IonHeader, IonToolbar, IonTitle, IonContent, EarthquakePage, SpringbootPage],
})
export class HomePage {
  constructor() {}
}
