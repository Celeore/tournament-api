import { Component, OnInit } from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {CrudService} from '../services/crud.service';

export interface Player {
  pseudo: string;
  points: number;
  ranking: number;
}

@Component({
  selector: 'app-player-list',
  templateUrl: './player-list.component.html',
  styleUrls: ['./player-list.component.css']
})
export class PlayerListComponent implements OnInit {
  p = 1;                      // Settup up pagination variable
  Players: Player[];                 // Save players data in Player's array.
  hideWhenNoPlayer = false; // Hide players data table when no player.
  noData = false;            // Showing No Player Message, when no player in database.
  constructor( public crudApi: CrudService, // Inject player CRUD services in constructor.
               public toastr: ToastrService // Toastr service for alert message
  ) { }

  ngOnInit(): void {
    this.dataState(); // Initialize player's list, when component is ready
    const s = this.crudApi.GetPlayersList();
    s.subscribe(data => this.Players = data);
  }

  dataState(): void {
    this.crudApi.GetPlayersList().subscribe(data => {

      if (data.length <= 0){
        this.hideWhenNoPlayer = false;
        this.noData = true;
      } else {
        this.hideWhenNoPlayer = true;
        this.noData = false;
      }
    });
  }

}
