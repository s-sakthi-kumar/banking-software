import { Component } from '@angular/core';
import { Router } from '@angular/router';


interface User {
  name: string;
  email: string;
  password: string;
}


@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  email = '';
  password = '';
  message = '';

  constructor(
    private router: Router
  ) {}


  login() {

    const users: User[] =
      JSON.parse(localStorage.getItem('users') || '[]');


    const user = users.find(
      u =>
        u.email === this.email &&
        u.password === this.password
    );


    if (user) {

      localStorage.setItem(
        'loggedInUser',
        JSON.stringify(user)
      );

      this.message = 'Login successful';

      // change this route when dashboard exists
      this.router.navigate(['/']);

    } else {

      this.message = 'Invalid email or password';

    }


}
}
