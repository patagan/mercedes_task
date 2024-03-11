import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DialogModule } from 'primeng/dialog';
import { FormsModule, NgForm }   from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CarConfiguration, CarFeatures } from '../shared/car.model';
import { CarService } from '../shared/car.service';
import { MessageService } from 'primeng/api';
import { DropdownModule } from 'primeng/dropdown';
import { MultiSelectModule } from 'primeng/multiselect';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-configuration-dialog',
  standalone: true,
  imports: [DialogModule, FormsModule,ButtonModule,InputTextModule, DropdownModule,MultiSelectModule, CommonModule],
  templateUrl: './configuration-dialog.component.html'
})
export class ConfigurationDialogComponent {
  @Input()
  selectedCar:CarConfiguration = new CarConfiguration();

  @Input()
  displayDialog!: boolean;

  @Input()
  carAvailableFeatures:CarFeatures=new CarFeatures()

  @Output() 
  showEvent = new EventEmitter<any>();

  @Output() 
  cancelEvent = new EventEmitter<any>();

  @Output() 
  saveEvent = new EventEmitter<any>();

  @Output() 
  updateSelectedCarEvent = new EventEmitter<any>();

  priceDifference:number = 0;

  constructor(private carService: CarService, private messageService: MessageService) { }

  onDialogShow() {
    this.showEvent.emit()
  }
  
  onDialogClose() {
    this.priceDifference = 0;
    this.updateSelectedCarEvent.emit(new CarConfiguration())

  }
  cancel() {
    this.cancelEvent.emit(!this.displayDialog);
  }

  onSubmit(carForm: NgForm) {
    if(!carForm.valid) return
    this.saveEvent.emit(this.selectedCar)
  }

  onConfigurationPrice() {
    let cachePrice = this.selectedCar.price;
    this.carService.getCarConfigurationPrice(this.selectedCar).subscribe({
      next: (response) => {
        this.updateSelectedCarEvent.emit(response)
        this.priceDifference = response.price - cachePrice;
      },
      error: (e) => {
        console.error('Login failed:', e);
        this.messageService.add({severity:'error', summary:'Error', detail:'Failed to get configurations'});
    
      }
    });
  }

}
