import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RestService} from '../../services/rest.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  private contextId: string;
  public labels: any[];
  public file: any;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private restService: RestService) {
  }

  ngOnInit(): void {
    this.contextId = null;

    this.route.params.subscribe((params) => {
      if (params.ctx && params.ctx.length == 30) {
        this.contextId = params.ctx;
      }
    });

    if (this.contextId == null) {
      this.getContextId();
    }
  }

  private getContextId(): void {
    this.restService.post('/context', {}, true)
      .subscribe((response) => {
        if (typeof response == 'string') {
          this.router.navigate(['test/' + response]);
        }
      });
  }

  public upload(event: any): void {
    const formData = new FormData(document.querySelector('form'));
    this.restService.post(
      '/upload/' + this.contextId,
      formData,
      false)
      .subscribe((response) => {
        console.log(response);
      });
  }
}
