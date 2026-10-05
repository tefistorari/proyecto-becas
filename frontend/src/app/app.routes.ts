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
import { ConvocatoriaForm } from './pages/admin/convocatoria-form/convocatoria-form';
import { ConvocatoriaDetalle } from './pages/admin/convocatoria-detalle/convocatoria-detalle';
import { CambioPassword } from './pages/cambio-password/cambio-password';
import { FormCarreraInvestigador } from './pages/alumno/postular/form-carrera-investigador/form-carrera-investigador';
import { authGuard } from './guards/auth-guard';
import { roleGuard } from './guards/role-guard';

export const routes: Routes = [
    // No auth
    { path: '', component: Home },
    { path: 'login', component: Login },
    { path: 'registro', component: Registro },

    // Auth
    { path: 'cambio-password', component: CambioPassword, canActivate: [authGuard] },

    // Auth y ALUMNO
    { path: 'alumno/dashboard', component: DashboardAlumno, canActivate: [authGuard, roleGuard('ALUMNO')] },
    { path: 'alumno/perfil', component: Perfil, canActivate: [authGuard, roleGuard('ALUMNO')] },
    { path: 'alumno/mis-postulaciones', component: MisPostulaciones, canActivate: [authGuard, roleGuard('ALUMNO')] },
    { path: 'alumno/postular/base/:convocatoriaId', component: FormularioBase, canActivate: [authGuard, roleGuard('ALUMNO')] },
    { path: 'alumno/postular/bis/:convocatoriaId', component: FormularioBis, canActivate: [authGuard, roleGuard('ALUMNO')] },
    { path: 'alumno/postular/binid/:convocatoriaId', component: FormularioBinid, canActivate: [authGuard, roleGuard('ALUMNO')] },
    { path: 'alumno/postular/carrera-investigador/:convocatoriaId', component: FormCarreraInvestigador, canActivate: [authGuard, roleGuard('ALUMNO')] },

    // Auth y ADMIN
    { path: 'admin/dashboard', component: DashboardAdmin, canActivate: [authGuard, roleGuard('ADMIN')] },
    { path: 'admin/postulaciones', component: Postulaciones, canActivate: [authGuard, roleGuard('ADMIN')] },
    { path: 'admin/convocatorias', component: Convocatorias, canActivate: [authGuard, roleGuard('ADMIN')] },
    { path: 'admin/convocatorias/nueva', component: ConvocatoriaForm, canActivate: [authGuard, roleGuard('ADMIN')] },
    { path: 'admin/convocatorias/:id', component: ConvocatoriaDetalle, canActivate: [authGuard, roleGuard('ADMIN')] },

    { path: '**', redirectTo: '' }
];
