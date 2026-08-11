import { Component } from '@angular/core';
import { Router } from '@angular/router';

interface User {
  name: string;
  email: string;
  password: string;
}

@Component({
  selector: 'app-register',
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})
export class RegisterComponent {

  user: User = {
    name: '',
    email: '',
    password: ''
  };

  message = '';

  constructor(private router: Router) {}

  register() {
    const users: User[] = JSON.parse(localStorage.getItem('users') || '[]');

    const existingUser = users.find(
      user => user.email === this.user.email
    );

    if (existingUser) {
      this.message = 'User already exists!';
      return;
    }

    users.push(this.user);

    localStorage.setItem('users', JSON.stringify(users));

    this.message = 'Registration successful!';

    this.user = {
      name: '',
      email: '',
      password: ''
    };

    setTimeout(() => {
      this.router.navigate(['/login']);
    }, 1000);
  }
}