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
  }
}
