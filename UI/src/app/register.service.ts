import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './models/user';
import { ServiceUrlProviderService } from './service-url-provider.service';

@Injectable()
export class RegisterService {

  private registerUrl : string;
  constructor(
    private http: HttpClient,
    private urlProv: ServiceUrlProviderService
  ) { 
    this.registerUrl = urlProv.getCompleteServiceUrl("user/register");
  }
  
  register(user: any): Observable<any>{
    return this.http.post<any>(this.registerUrl, user);
  }
}
