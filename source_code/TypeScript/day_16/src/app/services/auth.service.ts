import { Injectable } from '@angular/core';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private users: User[] = [
    {
      id: 1,
      username: 'admin',
      password: '12345'
    }
  ];

  constructor() {
    localStorage.setItem(
      'users',
      JSON.stringify(this.users)
    );
  }


  login(username: string, password: string): boolean {

    const users: User[] =
      JSON.parse(localStorage.getItem('users') || '[]');

    const user = users.find(
      u => u.username === username &&
           u.password === password
    );


    if(user){

      localStorage.setItem(
        'loggedUser',
        JSON.stringify(user)
      );

      return true;
    }

    return false;
  }


  logout(){

    localStorage.removeItem('loggedUser');

  }


  isLoggedIn(): boolean {

    return !!localStorage.getItem('loggedUser');

  }


  getUser(){

    return JSON.parse(
      localStorage.getItem('loggedUser') || 'null'
    );

  }

}
