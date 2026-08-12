import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function fechaNoPasadaValidator(): ValidatorFn {
    return(control: AbstractControl): ValidationErrors | null => {
        if(!control.value) return null;

        const fechaIngresada = new Date(control.value);
        const ahora = new Date();

        return fechaIngresada < ahora ? {fechaPasada: true} : null;
    };
}