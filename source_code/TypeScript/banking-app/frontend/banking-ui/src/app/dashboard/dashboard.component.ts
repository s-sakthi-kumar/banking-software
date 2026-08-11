import { Component, signal } from '@angular/core';

import { KebabCasePipe } from '../kebab-case.pipe';
@Component({
  // selector: 'dashboard' ,
  imports:[KebabCasePipe],
  template: `<div><p>{{ "Welcome to FFS bank"| kebabCase}}</p></div>`,
  styleUrls: ['../app.css'],
})
export class DashboardComponent {}

