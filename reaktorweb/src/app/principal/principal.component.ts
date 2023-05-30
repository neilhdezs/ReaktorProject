import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SimpleComputerDTO } from '../computer-dto';
import {interval, Subscription} from "rxjs";

@Component({
  selector: 'principal-compo',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements OnInit {
  computers?: SimpleComputerDTO[];
  subscription: Subscription | undefined;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.getComputers();
    this.subscription = interval(1000).subscribe(x => { this.getComputers(); });

  }

  private getComputers() {
    this.http.get<SimpleComputerDTO[]>('http://localhost:8084/computer').subscribe(data => {
      this.computers = data;
    });
  }
}
