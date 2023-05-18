import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ComputerCardComponent } from './computer-card/computer-card.component';
import { ComputerDetailsComponent } from './computer-details/computer-details.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { PrincipalComponent } from './principal/principal.component';

@NgModule({
  declarations: [
    AppComponent,
    ComputerCardComponent,
    ComputerDetailsComponent,
    PrincipalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
