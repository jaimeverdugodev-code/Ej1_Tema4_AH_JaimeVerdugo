export interface Tipo {
  id: number;
  nombre: string;
  descripcion?: string | null;
}

export interface Camisa {
  id: number;
  nombre: string;
  talla: string | null;
  color: string | null;
  precio: number | null;
  imagenUrl?: string | null;
  lat?: number | null;
  lng?: number | null;
  tipo?: Tipo | null;
}
