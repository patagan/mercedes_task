import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import {provideHttpClient, withFetch, withInterceptors} from '@angular/common/http';

import { routes } from './app.routes';
import { provideAnimations } from '@angular/platform-browser/animations';
import { authInterceptor } from './auth.guard';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideAnimations(),provideHttpClient(withFetch(),withInterceptors(
    [authInterceptor]
))]
};
