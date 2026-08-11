import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  username = '';
  password = '';

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}


  login() {

    const success = this.auth.login(
      this.username,
      this.password
    );


    if (success) {
      this.router.navigate(['/transactions']);
    }
    else {
      alert('Invalid login');
    }

  }

}
