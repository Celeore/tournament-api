import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CrudService, Player} from '../services/crud.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-add-player',
  templateUrl: './add-player.component.html',
  styleUrls: ['./add-player.component.css']
})
export class AddPlayerComponent implements OnInit {
  public playerForm: FormGroup;  // Define FormGroup to player's form

  constructor(public crudApi: CrudService,  // CRUD API services
              public fb: FormBuilder,       // Form Builder service for Reactive forms
              public toastr: ToastrService  // Toastr service for alert message
  ) { }

  ngOnInit(): void {
    this.crudApi.GetPlayersList();  // Call GetPlayersList() before main form is being called
    this.playenForm();              // Call player form when component is ready
  }

  // Reactive player form
  playenForm(): void {
    this.playerForm = this.fb.group({
      pseudo: ['', [Validators.required, Validators.minLength(2)]],
    });
  }

  // Accessing form control using getters
  get pseudo(): AbstractControl {
    return this.playerForm.get('pseudo');
  }

  ResetForm(): void {
    this.playerForm.reset();
  }

  submitPlayerData(): void {
    this.crudApi.AddPlayer(this.playerForm.value).subscribe(); // Submit player data using CRUD API
    this.toastr.success(this.playerForm.controls.pseudo.value + ' successfully added!'); // Show success message when data is successfully submited
    this.ResetForm();  // Reset form when clicked on reset button
  };
}
