import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { UserService } from '../shared/user.service';
import { MessageService } from 'primeng/api';
import { User } from '../shared/user.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [InputTextModule, PasswordModule, ButtonModule,FormsModule,RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  userLogin:User = new User('','');

  constructor(private router: Router, private userService: UserService, private messageService: MessageService) {}

  login() {
    this.userService.login(this.userLogin)
    .subscribe({
      next: (response) => {
        console.log('Login successful:', response);
        // Save the JWT token
        this.userService.saveToken(response.token);
        // Redirect or navigate to other pages
        this.messageService.add({severity:'success', summary:'Success', detail:'Login successful'});
        this.router.navigate([''])
      },
      error: (e) => {
        console.error('Login failed:', e);
        this.messageService.add({severity:'error', summary:'Error', detail:'Login failed'});
    
      }
    });
  }

}
