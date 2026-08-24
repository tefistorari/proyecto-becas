import { Service } from '@angular/core';
import { getCountries, getStatesOfCountry, getCitiesOfState } from '@countrystatecity/countries-browser';
import { getTranslation } from '@countrystatecity/translations';

@Service()
export class UbicacionService {

    getNacionalidades() {
        return getCountries();
    }

    getProvincias() {
        return getStatesOfCountry('AR');
    }

    getLocalidades(codigoProvincia: string) {
        return getCitiesOfState('AR', codigoProvincia);
    }

    getNombreNacionalidad(codigoPais: string, idioma: string) {
        return getTranslation(codigoPais, idioma);
    }
}
