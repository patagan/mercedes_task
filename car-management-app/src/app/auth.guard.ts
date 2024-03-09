import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { UserService } from "./shared/user.service";
import { inject } from "@angular/core";
import { HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";

export const canActivate: CanActivateFn = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) => {
    const authService = inject(UserService);
    const router = inject(Router);
    if(authService.getToken()) {
        return true;
    } else {
        router.navigate(['login']);
        return false;
    }
    
};

export const authInterceptor: HttpInterceptorFn = (
    req: HttpRequest<any>,
    next: HttpHandlerFn
) => {
    const authService = inject(UserService);
    const token = authService.getToken()
    if (token && !req.url.includes("/login")) {
        const cloned = req.clone({
        setHeaders: {
            authorization: "Bearer " + token,
        },
        });
        return next(cloned);
    } else {
        return next(req);
    }
};