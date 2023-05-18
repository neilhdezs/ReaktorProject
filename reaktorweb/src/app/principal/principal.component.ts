import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SimpleComputerDTO } from '../computer-dto';

@Component({
  selector: 'principal-compo',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements OnInit {
  computers?: SimpleComputerDTO[];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<SimpleComputerDTO[]>('http://localhost:8084/computer').subscribe(data => {
      this.computers = data;
    });

    this.computers = [
      {
        malwareCount: 1,
        id: "1",
        location: "miCasa",
        responsable: "Yo",
        computerOn: true
      },
      {
        malwareCount: 1,
        id: "1",
        location: "miCasa",
        responsable: "Yo",
        computerOn: false
      },
      {
        malwareCount: 1,
        id: "1",
        location: "miCasa",
        responsable: "Yo",
        computerOn: true
      },
      {
        malwareCount: 1,
        id: "1",
        location: "miCasa",
        responsable: "Yo",
        computerOn: true
      }
    ]

  }
}
