import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Earthquake as EarthquakeModel } from '../models/earthquake.model';

@Injectable({
  providedIn: 'root',
})
export class EarthquakeService {
  private readonly feedUrl = 'https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson';

  constructor(private http: HttpClient) {}

  getLatestEarthquake(): Observable<EarthquakeModel | null> {
    return this.http.get<any>(this.feedUrl).pipe(
      map(res => {
        if (res?.features?.length) {
          const f = res.features[0];
          const props = f.properties || {};
          const coords = (f.geometry && f.geometry.coordinates) || [null, null, null];
          return {
            mag: props.mag ?? null,
            place: props.place ?? null,
            time: props.time ?? null,
            url: props.url ?? null,
            longitude: coords[0] ?? null,
            latitude: coords[1] ?? null,
            depth: coords[2] ?? null
          } as EarthquakeModel;
        }
        return null;
      })
    );
  }
}
