import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { CarConfiguration } from '../shared/car.model';

@Component({
  selector: 'app-list-element',
  standalone: true,
  imports: [ButtonModule,CardModule],
  templateUrl: './list-element.component.html',
  styleUrl: './list-element.component.scss'
})
export class ListElementComponent {
  @Input()
  configurationInformation!: CarConfiguration;

  @Output() 
  editEvent = new EventEmitter<any>();

  @Output()
  deleteEvent = new EventEmitter<any>();

  onEdit() {
    this.editEvent.emit(this.configurationInformation)
  }

  onDelete() {
    this.deleteEvent.emit(this.configurationInformation.id)
  }

}
