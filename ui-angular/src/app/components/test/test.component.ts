import { Component, OnInit } from '@angular/core';
import {RestService} from '../../services/rest.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  public labels: any[];

  constructor(private restService: RestService) { }

  ngOnInit(): void {
    this.restService.post('/context', {}).subscribe((response) => {
      console.log(response);
    });
  }
}
