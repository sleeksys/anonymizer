import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private ENDPOINT = 'https://api.sleeksys.com/anonymizer';

  constructor(private http: HttpClient) {
  }

  private getHttpOptions(responseTypeText): any {
    const options: any = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };

    // response type text
    if (responseTypeText) {
      options.headers.set('Content-Type', 'text/plain; charset=utf-8');
      options.responseType = 'text';
    }

    return options;
  }

  public get(url: string): Observable<any> {
    return this.http.get(this.ENDPOINT + url);
  }

  public post(url: string, postBody: any, responseTypeText: boolean): Observable<any> {
    return this.http.post(this.ENDPOINT + url, postBody, this.getHttpOptions(responseTypeText));
  }

  public put(url: string, postBody: any): Observable<any> {
    return this.http.put(this.ENDPOINT + url, postBody);
  }

  public delete(url: string, postBody: any, callbackFn: any): void {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: postBody
    };

    this.http.delete(
      this.ENDPOINT + url, options)
      .subscribe(
        response => {
          callbackFn(response);
        },
        err => {
          console.log('Error', err.error);
          callbackFn({});
        }
      );
  }
}
