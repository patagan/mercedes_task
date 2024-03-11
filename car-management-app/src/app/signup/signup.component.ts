import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { UserService } from '../shared/user.service';
import { MessageService } from 'primeng/api';
import { User } from '../shared/user.model';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [InputTextModule, PasswordModule, ButtonModule,FormsModule,RouterModule],
  templateUrl: './signup.component.html'
})
export class SignupComponent {
  userLogin:User = new User('','');

  constructor(private router: Router, private userService: UserService, private messageService: MessageService) {}

  signup(signeupForm:NgForm) {
    if(!signeupForm.valid) return
    this.userService.register(this.userLogin)
    .subscribe({
      next: () => {
        // Redirect or navigate to other pages
        this.messageService.add({severity:'success', summary:'Success', detail:'Registration successful'});
        this.router.navigate(['/login'])
      },
      error: (e) => {
        console.error('Login failed:', e);
        this.messageService.add({severity:'error', summary:'Error', detail:'Registration failed'});
    
      }
    });
  }
}
