import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HistoryComponent } from './history/history.component';
import { HomeComponent } from './home/home.component';
import { LeftoversComponent } from './leftovers/leftovers.component';
import { ProductComponent } from './product/product.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },

  {
    path: 'home',
    component: HomeComponent
  },

  {
    path: 'history',
    component: HistoryComponent
  },

  {
    path: 'product',
    component: ProductComponent
  },

  {
    path: 'leftovers',
    component: LeftoversComponent
  },



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
