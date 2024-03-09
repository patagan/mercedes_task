import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../shared/user.service';
import { MenubarModule } from 'primeng/menubar';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule, MenubarModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  menuItems = [
    {
      label: 'Logout',
      icon: 'pi pi-fw pi-power-off',
      command: () => {
        this.logout();
      }
    }
  ];

  constructor(private router: Router, private userService: UserService) { }

  logout() {
    this.userService.clearToken();
    this.router.navigate(['/login']);
  }
}
