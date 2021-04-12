import {Component, OnInit} from '@angular/core';
import {CrudService} from '../services/crud.service';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Location} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-edit-player',
  templateUrl: './edit-player.component.html',
  styleUrls: ['./edit-player.component.css']
})
export class EditPlayerComponent implements OnInit {
  editForm: FormGroup;  // Define FormGroup to player's edit form
  pseudo = '';

  constructor(
    private crudApi: CrudService,       // Inject CRUD API in constructor
    private fb: FormBuilder,            // Inject Form Builder service for Reactive forms
    private location: Location,         // Location service to go back to previous component
    private actRoute: ActivatedRoute,   // Activated route to get the current component's inforamation
    private router: Router,             // Router service to navigate to specific component
    private toastr: ToastrService       // Toastr service for alert message
  ) {}

  ngOnInit(): void {
    this.updatePlayerData();   // Call updatePlayerData() as soon as the component is ready
    this.pseudo = this.actRoute.snapshot.paramMap.get('pseudo');  // Getting current component's id or information using ActivatedRoute service
    this.crudApi.GetPlayer(this.pseudo).subscribe(data => {
      this.editForm.patchValue({
        points: data.points
      });  // Using SetValue() method, It's a ReactiveForm's API to store intial value of reactive form
    });
  }

  get points(): AbstractControl {
    return this.editForm.get('points');
  }

  // Go back to previous component
  goBack(): void {
    this.location.back();
  }

  // Contains Reactive Form logic
  updatePlayerData(): void {
    this.editForm = this.fb.group({
      points: ['', [Validators.required, Validators.min(1)]]
    });
  }

  // Below methods fire when somebody click on submit button
  updateForm(): void {
    this.crudApi.UpdatePlayerPoints(this.pseudo, this.editForm.value).subscribe();       // Update player data using CRUD API
    this.toastr.success(this.pseudo + ' updated successfully');   // Show succes message when data is successfully submited
    this.router.navigate(['view-players']);               // Navigate to player's list page when player data is updated
  }

}
