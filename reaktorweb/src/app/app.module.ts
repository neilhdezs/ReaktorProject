import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ComputerCardComponent } from './computer-card/computer-card.component';
import { ComputerDetailsComponent } from './computer-details/computer-details.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { PrincipalComponent } from './principal/principal.component';
import { MalwareComponent } from './malware/malware.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    ComputerCardComponent,
    ComputerDetailsComponent,
    PrincipalComponent,
    MalwareComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
