import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Login } from './pages/login/login';
import { Registro } from './pages/registro/registro';
import { Perfil } from './pages/alumno/perfil/perfil';
import { MisPostulaciones } from './pages/alumno/mis-postulaciones/mis-postulaciones';
import { FormularioBase } from './pages/alumno/postular/formulario-base/formulario-base';
import { FormularioBis } from './pages/alumno/postular/formulario-bis/formulario-bis';
import { FormularioBinid } from './pages/alumno/postular/formulario-binid/formulario-binid';
import { DashboardAdmin } from './pages/admin/dashboard-admin/dashboard-admin';
import { Postulaciones } from './pages/admin/postulaciones/postulaciones';
import { Convocatorias } from './pages/admin/convocatorias/convocatorias';
import { DashboardAlumno } from './pages/alumno/dashboard-alumno/dashboard-alumno';


export const routes: Routes = [
    {path: '', component: Home},
    {path: 'login', component: Login},
    {path: 'registro', component: Registro},

    {path: 'alumno/dashboard', component: DashboardAlumno},
    {path: 'alumno/perfil', component: Perfil},
    {path: 'alumno/mis-postulaciones', component: MisPostulaciones},
    {path: 'alumno/postular/base', component: FormularioBase},
    {path: 'alumno/postular/bis', component: FormularioBis},
    {path: 'alumno/postular/binid', component: FormularioBinid},

    {path: 'admin/dashboard', component: DashboardAdmin},
    {path: 'admin/postulaciones', component: Postulaciones},
    {path: 'admin/convocatorias', component: Convocatorias},

    {path: '**', redirectTo: ''}
];
