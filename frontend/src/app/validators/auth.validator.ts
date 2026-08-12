import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function passwordsMatchValidator(): ValidatorFn {
    return (group: AbstractControl): ValidationErrors | null => {
        const password = group.get('password')?.value;
        const confirmPassword = group.get('confirmarPassword')?.value;

        if(!password || !confirmPassword) return null;

        return password === confirmPassword ? null : { passwordsNoCoinciden: true};
    };
}