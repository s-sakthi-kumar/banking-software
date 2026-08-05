import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CustomComponent } from './custom-component';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet,CustomComponent,RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('banking-ui');
}
