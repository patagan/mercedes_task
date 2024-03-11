import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { CarConfiguration } from '../shared/car.model';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService, ConfirmEventType } from 'primeng/api';

@Component({
  selector: 'app-list-element',
  standalone: true,
  imports: [ButtonModule,CardModule, ConfirmDialogModule],
  templateUrl: './list-element.component.html',
  providers: [ConfirmationService] 
})
export class ListElementComponent {
  @Input()
  configurationInformation: CarConfiguration = new CarConfiguration();

  @Output() 
  editEvent = new EventEmitter<any>();

  @Output()
  deleteEvent = new EventEmitter<any>();

  constructor(private confirmationService: ConfirmationService, private messageService: MessageService) {}

  onEdit() {
    this.editEvent.emit(this.configurationInformation)
  }

  onDelete() {
    this.deleteEvent.emit(this.configurationInformation.id)
  }

  confirm2(event: Event) {
      this.confirmationService.confirm({
          target: event.target as EventTarget,
          message: 'Do you want to delete this configuration?',
          header: 'Delete Confirmation',
          icon: 'pi pi-info-circle',
          acceptButtonStyleClass:"p-button-danger p-button-text",
          rejectButtonStyleClass:"p-button-text p-button-text",
          acceptIcon:"none",
          rejectIcon:"none",

          accept: () => {
              this.deleteEvent.emit(this.configurationInformation.id)
          },
          reject: () => {
              this.messageService.add({ severity: 'info', summary: 'Rejected', detail: 'You have rejected deletion' });
          }
      });
  }

}
