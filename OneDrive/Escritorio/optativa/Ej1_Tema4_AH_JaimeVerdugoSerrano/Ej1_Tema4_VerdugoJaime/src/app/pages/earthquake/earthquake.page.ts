import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonContent,
  IonHeader,
  IonTitle,
  IonToolbar,
  IonButton,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardSubtitle,
  IonCardContent,
} from '@ionic/angular/standalone';
import { EarthquakeService } from '../../services/earthquake';
import { Earthquake } from '../../models/earthquake.model';

@Component({
  selector: 'app-earthquake',
  templateUrl: './earthquake.page.html',
  styleUrls: ['./earthquake.page.scss'],
  standalone: true,
  imports: [
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonButton,
    IonCard,
    IonCardHeader,
    IonCardTitle,
    IonCardSubtitle,
    IonCardContent,
    CommonModule,
    FormsModule,
  ],
})
export class EarthquakePage implements OnInit {
  latest: Earthquake | null = null;
  loading = false;
  error: string | null = null;

  constructor(private eqService: EarthquakeService) {}

  ngOnInit() {}

  fetchLatest() {
    this.loading = true;
    this.error = null;
    this.eqService.getLatestEarthquake().subscribe({
      next: (data) => {
        this.latest = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error fetching earthquake data';
        this.loading = false;
      },
    });
  }
}
