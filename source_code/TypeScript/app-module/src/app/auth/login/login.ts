import { Component } from '@angular/core';
import { Router } from '@angular/router';

interface User {
  name: string;
  email: string;
  password: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  email = '';
  password = '';

  message = '';

  constructor(private router: Router) {}

  login() {

    const users: User[] = JSON.parse(
      localStorage.getItem('users') || '[]'
    );

    const user = users.find(
      user =>
        user.email === this.email &&
        user.password === this.password
    );

    if (user) {

      localStorage.setItem(
        'loggedInUser',
        JSON.stringify(user)
      );

      this.message = 'Login successful!';

      this.router.navigate(['/dashboard']);

    } else {

      this.message = 'Invalid email or password';

    }
  }
}