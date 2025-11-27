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
  IonCardContent,
  IonList,
  IonItem,
  IonLabel,
  IonImg,
} from '@ionic/angular/standalone';
import { RenderapiService } from '../../services/renderapi';
import { Camisa } from '../../models/camisa.model';

@Component({
  selector: 'app-springboot',
  templateUrl: './springboot.page.html',
  styleUrls: ['./springboot.page.scss'],
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
    IonCardContent,
    IonList,
    IonItem,
    IonLabel,
    IonImg,
    CommonModule,
    FormsModule,
  ],
})
export class SpringbootPage implements OnInit {
  data: Camisa[] = [];
  loading = false;
  error: string | null = null;
  rawResponse: string | null = null;

  constructor(private render: RenderapiService) { }

  ngOnInit() {
    // Auto-fetch when the page loads so data appears without clicking
    this.fetchFromRender();
  }

  fetchFromRender() {
    this.loading = true;
    this.error = null;
    this.render.get('/api/camisas').subscribe({
      next: (res: any) => {
        if (Array.isArray(res)) {
          this.data = res as Camisa[];
        } else if (res && Array.isArray(res.data)) {
          this.data = res.data as Camisa[];
        } else if (res && typeof res === 'object') {
          // single object -> wrap into array
          this.data = [res as Camisa];
        } else if (typeof res === 'string') {
          // server returned non-JSON text (HTML or message)
          this.error = `Server returned non-JSON response`;
          this.rawResponse = res;
          this.data = [];
        } else {
          this.data = [];
        }
        this.loading = false;
      },
      error: (err) => {
        console.error('Render API error:', err);
        const status = err?.status;
        const statusText = err?.statusText || err?.message || '';
        this.error = `Error fetching from Render API${status ? ' (' + status + ')' : ''}: ${statusText}`;
        this.data = [];
        // if the HTTP client gave a text body, try to capture it
        if (err?.error && typeof err.error === 'string') {
          this.rawResponse = err.error;
        }
        this.loading = false;
      }
    });
  }

}
