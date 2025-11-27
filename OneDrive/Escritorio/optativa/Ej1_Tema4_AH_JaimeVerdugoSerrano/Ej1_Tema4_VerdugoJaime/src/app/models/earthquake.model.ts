export interface Earthquake {
  mag: number | null;
  place: string | null;
  time: number | null; // epoch ms
  url?: string | null;
  longitude?: number | null;
  latitude?: number | null;
  depth?: number | null;
}
