import { Component } from '@angular/core';
import { Router } from '@angular/router';


interface User {
  name: string;
  email: string;
  password: string;
}


@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class RegisterComponent {


  user: User = {
    name: '',
    email: '',
    password: ''
  };


  message = '';


  constructor(
    private router: Router
  ) {}


  register() {


    const users: User[] =
      JSON.parse(localStorage.getItem('users') || '[]');


    const exists = users.find(
      u => u.email === this.user.email
    );


    if (exists) {

      this.message = 'User already exists';

      return;

    }


    users.push(this.user);


    localStorage.setItem(
      'users',
      JSON.stringify(users)
    );


    this.message = 'Registration successful';


    setTimeout(() => {

      this.router.navigate(['/auth/login']);

    }, 1000);


  }

}
