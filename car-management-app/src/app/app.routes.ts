import { Routes } from '@angular/router';
import { ConfigurationListComponent } from './configuration-list/configuration-list.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { canActivate } from './auth.guard';

export const routes: Routes = [
    {
        path: '',
        component: ConfigurationListComponent,
        title: 'Home page',
        canActivate:[canActivate]
    },
    {
      path: 'login',
      component: LoginComponent,
      title: 'Login page',
    },
    {
      path: 'register',
      component: SignupComponent,
      title: 'Signup page',
    },
];