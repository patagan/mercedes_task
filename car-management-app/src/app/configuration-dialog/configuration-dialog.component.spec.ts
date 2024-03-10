import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MessageService } from 'primeng/api';
import { ConfigurationDialogComponent } from './configuration-dialog.component';
import { CarConfiguration } from '../shared/car.model';

describe('ConfigurationDialogComponent', () => {
  let component: ConfigurationDialogComponent;
  let fixture: ComponentFixture<ConfigurationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers:[MessageService],
      imports: [ConfigurationDialogComponent,
        FormsModule,
        ButtonModule,
        HttpClientTestingModule]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConfigurationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    component.selectedCar = new CarConfiguration()
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });
});
