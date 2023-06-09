import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {ComputerDTO} from "../computer-dto";
import Axios from "axios";

@Component({
  selector: 'app-computer-details',
  templateUrl: './computer-details.component.html',
  styleUrls: ['./computer-details.component.css']
})
export class ComputerDetailsComponent implements OnInit {
  computerId?: string;
  computer?: ComputerDTO;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.computerId = this.route.snapshot.paramMap.get('id')!;
    Axios.get("http://143.47.41.0:8084" + '/reaktor/' + this.computerId)
      .then( response => {
        this.computer = response.data;
        console.log(this.computer)
      })
      .catch( error => console.log(error))
  }
}
