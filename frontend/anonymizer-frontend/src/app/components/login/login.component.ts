import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {RestClientService} from "../../services/rest-client.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private mode: number=0;

  constructor(private router:Router,
              private restClientService: RestClientService) { }

  ngOnInit() {
  }

  onLogin(formData) {

    console.log("Data" + " " + formData);
    this.restClientService.login(formData).subscribe(resp =>{
      let token = resp.headers.get('Authorization');
      this.restClientService.saveToken(token);
      this.router.navigateByUrl("/uploadFile");
    }, error => {
      this.mode=1;
    });

  }

  onRegister() {
    this.router.navigateByUrl("/register");
  }
}
