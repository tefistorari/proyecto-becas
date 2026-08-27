import { Pipe, PipeTransform } from '@angular/core';

/**
 * Convierte un valor de enum en SCREAMING_SNAKE_CASE (ej. 'SIN_OBRASOCIAL')
 * en un texto legible con mayúscula inicial (ej. 'Sin obrasocial').
 *
 * Uso en template: {{ g | labelEnum }}
 *
 * Es una transformación genérica (reemplaza "_" por espacio y capitaliza).
 * Si en algún caso necesitás un texto más prolijo o específico
 * (ej. "CON_OBRASOCIAL" -> "Con obra social", con espacio en "obra social"),
 * conviene armar un Record<TuEnum, string> aparte para ese caso puntual,
 * como ya hicieron con CategoriaInvestigadorDescripcion.
 */

@Pipe({
  name: 'labelEnum',
  standalone: true,
})
export class LabelEnumPipe implements PipeTransform {
  transform(value: unknown): string {
        if (value === null || value === undefined || value === '') {
      return '';
    }
 
    const texto = String(value).toLowerCase().replace(/_/g, ' ');
    return texto.charAt(0).toUpperCase() + texto.slice(1);
  }
}
