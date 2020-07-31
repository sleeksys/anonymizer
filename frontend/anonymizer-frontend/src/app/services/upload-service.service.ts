import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpRequest} from '@angular/common/http';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UploadServiceService {
  public host:string = environment.backendBaseUri;

  constructor(private http: HttpClient) { }

  uploadFile(file: File) {

    let formdata: FormData = new FormData();

    formdata.append('file', file);



    const req = new HttpRequest('POST', this.host + '/uploadFile', formdata,{
      reportProgress: true,
      responseType: 'text'
    })
    return this.http.request(req);

  }
}
