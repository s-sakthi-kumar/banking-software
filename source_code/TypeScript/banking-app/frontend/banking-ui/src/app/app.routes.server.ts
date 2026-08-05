import { RenderMode, ServerRoute } from '@angular/ssr';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TransactionComponent } from './transaction/transaction.component';

export const serverRoutes: ServerRoute[] = [
  // {
  //   path: 'dashboard',
  //   renderMode: RenderMode.Prerender,
  // },
  // {
  //   path: 'transaction',
  //   renderMode: RenderMode.Prerender,
  // },
  {
    path: '**',
    renderMode: RenderMode.Prerender,
  },
];
