import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RenderapiService {
  private base = environment.renderApiUrl || '';

  constructor(private http: HttpClient) {}

  /**
   * Get request that first retrieves the response as text and tries to parse JSON.
   * This avoids HttpClient JSON parse errors when server returns non-JSON with 200 status.
   */
  get(endpoint: string = ''): Observable<any> {
    const url = `${this.base}${endpoint}`;
    console.debug('RenderapiService GET ->', url);
    return this.http.get(url, { responseType: 'text' }).pipe(
      map((text) => {
        // try JSON parse first
        try {
          return JSON.parse(text);
        } catch (e) {
          // attempt to extract JSON substring from text (first { or [ to last } or ])
          const firstBrace = Math.min(
            ...['{', '[']
              .map((ch) => text.indexOf(ch))
              .filter((i) => i >= 0)
          );
          const lastBrace = Math.max(
            ...['}', ']']
              .map((ch) => text.lastIndexOf(ch))
              .filter((i) => i >= 0)
          );
          if (!isFinite(firstBrace) || !isFinite(lastBrace) || lastBrace <= firstBrace) {
            // no JSON like substring found
            return text;
          }
          const candidate = text.substring(firstBrace, lastBrace + 1);
          try {
            return JSON.parse(candidate);
          } catch (e2) {
            // still can't parse -> return raw text for debugging
            return text;
          }
        }
      })
    );
  }
}
