import { Component, OnInit } from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  private user: any;
  mode:number=0;
  private errorMessage: string;
  constructor(private restClientService: RestClientService) { }

  ngOnInit() {
  }
  onRegister(user) {
    this.restClientService.register(user).subscribe(data=>{
      console.log(" new Task" + " " + data);
      this.user=data;
      this.mode=1;
    },error => {
      this.errorMessage = error.message;
      this.mode = 0;
    })
  }
}
