import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {RestClientService} from "./services/rest-client.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'anonymizer-frontend';

  constructor(private  router: Router,
              private restClientService: RestClientService) {
  }

  onLogout() {
    this.restClientService.logout();
    this.router.navigateByUrl('/login');
  }

}
