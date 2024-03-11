import { Component, OnInit } from '@angular/core';
import { CarConfiguration, CarFeatures } from '../shared/car.model';
import { CarService } from '../shared/car.service';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import {ListElementComponent} from '../list-element/list-element.component';
import { HeaderComponent } from '../header/header.component';
import { MessageService } from 'primeng/api';
import { ConfigurationDialogComponent } from '../configuration-dialog/configuration-dialog.component';
import { Router } from '@angular/router';


@Component({
  selector: 'app-configuration-list',
  standalone: true,
  imports: [ButtonModule,CardModule, ListElementComponent,HeaderComponent, ConfigurationDialogComponent],
  templateUrl: './configuration-list.component.html'
})
export class ConfigurationListComponent implements OnInit {

  cars: CarConfiguration[] = [];
  selectedCar: CarConfiguration =new CarConfiguration();
  displayDialog: boolean = false;
  carAvailableFeatures: CarFeatures = new CarFeatures();

  constructor(private carService: CarService, private messageService: MessageService, private router: Router) { }

  ngOnInit(): void {
    this.retrieveConfigurations()
  }

  cancel(data: any) {
    this.displayDialog = false
  }

  getAllCarFeatures() {
    this.carService.getCarFeatures().subscribe({
      next: (response) => {
        this.carAvailableFeatures = response;
      },
      error: (e) => {
        this.handleHttpError(e, 'Failed to get all configurations');
      }
    });
  }

  saveConfiguration(data: any) {
    this.carService.saveCar(this.selectedCar).subscribe({
      next: () => {
        this.messageService.add({severity:'success', summary:'Success', detail:'Configuration saved successfuly'});
        this.retrieveConfigurations()
      },
      error: (e) => {
        this.handleHttpError(e, 'Failed to save user configuration');
      }
    });
    this.displayDialog = false
  }

  retrieveConfigurations() {
    this.carService.getMyCarConfigurations().subscribe({
      next: (response) => {
        this.cars = response;
      },
      error: (e) => {
        this.handleHttpError(e, 'Failed to get configurations');
      }
    });
  }

  onDelete(data: any) {
    this.carService.deleteCar(data).subscribe({
      next: () => {
        this.messageService.add({severity:'success', summary:'Success', detail:'Configuration  deleted:' + data});
        this.retrieveConfigurations();
      },
      error: (e) => {
        this.handleHttpError(e, 'Failed to delete configuration with id: ' + data);
      }
    });
  }

  handleHttpError(error:any, errorDetail:string) {
    if(error.status === 401) {
      this.messageService.add({severity:'warn', summary:'Warn', detail:'Session expired'});
      
      this.router.navigate(['login'])
    } else {
      this.messageService.add({severity:'error', summary:'Error', detail:errorDetail});
    }
  }

  showDialogToAdd() {
    this.selectedCar;
    this.displayDialog = true;
  }

  showDialogToEdit(data: any) {
    this.selectedCar = { ...data };
    this.displayDialog = true;
  }

  onUpdateSelectedCar(data:any) {
    this.selectedCar = data;
  }

}
