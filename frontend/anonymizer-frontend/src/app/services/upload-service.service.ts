import { Injectable } from '@angular/core';
import {HttpClient, HttpRequest} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UploadServiceService {
  public host:string = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  uploadFile(file:File) {

    let formdata: FormData = new FormData();

    formdata.append('file', file);

    const req = new HttpRequest('POST', this.host + '/uploadFile', formdata,{
      reportProgress: true,
      responseType: 'text'
    })
    return this.http.request(req);

  }
}
