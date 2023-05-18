import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComputerDetailsComponent } from './computer-details/computer-details.component';
import {PrincipalComponent} from "./principal/principal.component";

const routes: Routes = [
  { path: '', component: PrincipalComponent },
  { path: 'details/:id', component: ComputerDetailsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
