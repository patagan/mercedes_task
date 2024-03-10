import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigurationListComponent } from './configuration-list.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MessageService } from 'primeng/api';

describe('ConfigurationListComponent', () => {
  let component: ConfigurationListComponent;
  let fixture: ComponentFixture<ConfigurationListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers:[MessageService],
      imports: [ConfigurationListComponent, HttpClientTestingModule]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConfigurationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
