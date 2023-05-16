import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-computer-details',
  template: '<div> Detalles de: {{computerId}}',
  templateUrl: './computer-details.component.html',
  styleUrls: ['./computer-details.component.css']
})
export class ComputerDetailsComponent implements OnInit {
  computerId?: string;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.computerId = this.route.snapshot.paramMap.get('id')!;
    // Aquí puedes hacer una petición HTTP para obtener los detalles del ordenador según el ID
  }
}
