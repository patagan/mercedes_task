import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CarConfiguration } from './car.model';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private http: HttpClient) { }

  getMyCarConfigurations(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/getConfigurations');
  }

  getCarFeatures(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/getCarFeatures');
  }

  saveCar(carConfiguration: CarConfiguration): Observable<any>  {
    return this.http.post<any>('http://localhost:8080/addConfiguration',carConfiguration);
  }


  deleteCar(id: number) {
    return this.http.delete<any>('http://localhost:8080/deleteConfiguration',{body:id});
  }

  getCarConfigurationPrice(carConfiguration: CarConfiguration) {
    return this.http.post<any>('http://localhost:8080/getConfigurationPrice',carConfiguration);
  }

}
