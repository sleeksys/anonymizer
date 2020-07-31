import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpRequest} from "@angular/common/http";
import {JwtHelperService} from "@auth0/angular-jwt";
import {User} from "../entities/User";
import {environment} from "../../environments/environment";
import {hostReportError} from "rxjs/internal-compatibility";

@Injectable({
  providedIn: 'root'
})
export class RestClientService {
  admin:number = 0;
  private host:string= environment.backendBaseUri;
  private jwToken:string;
  public roles:Array<any>[];
  constructor(private http:HttpClient) { }

  public logout(){
    localStorage.removeItem('token');
    this.admin = 0;
  }

  public  saveToken(jwToken){
    this.jwToken = jwToken;
    localStorage.setItem("token", jwToken);
    let jwHelper = new JwtHelperService();
    this.roles = jwHelper.decodeToken(this.jwToken).roles;
    console.log("roles" + " " + this.roles);
  }

  public login(user:User){

    if(user.username === "admin"){
      this.admin= 1;
    }
    console.log("request : " + " " + this.host+"/login");
    return this.http.post(this.host+"/login", user,{observe: 'response'});
  }

  public  register(user){
    return this.http.post(this.host+"/users",user);
  }

  public loadToken() {
    this.jwToken= localStorage.getItem('token');
    return this.jwToken;
  }

  public getLabels(){
    if(this.jwToken==null) this.loadToken();
    return this.http.get(this.host+"/labels",{headers: new HttpHeaders({'Authorization':this.jwToken})})
  }

  uploadFile(file: File) {

    let formData: FormData = new FormData();
    let headers = new HttpHeaders();

    headers.append('Authorization', this.jwToken);
    formData.append('file', file);

    const req = new HttpRequest('POST', this.host + '/uploadFile', formData,{
      reportProgress: true,
      responseType: 'text' ,
      headers: headers
    })
    return this.http.request(req);

  }

}
