import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SimpleComputerDTO } from './computer-dto';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  computers?: SimpleComputerDTO[];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<SimpleComputerDTO[]>('ruta-a-tu-api').subscribe(data => {
      this.computers = data;
    });
  }
}
