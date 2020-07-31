import { Component, OnInit } from '@angular/core';
import {UploadServiceService} from '../../services/upload-service.service';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {RestClientService} from "../../services/rest-client.service";

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.css']
})
export class UploadFileComponent implements OnInit {
  progress: any;
  private selectedFiles: any;
  private currentFileUpload: any;

  constructor(private uploadService:UploadServiceService,
              private restClientService: RestClientService) { }

  ngOnInit() {
  }

  onSelectedFile(event) {
    this.selectedFiles = event.target.files;
  }

  uploadFile() {
    this.progress = 0;

    this.currentFileUpload =this.selectedFiles.item(0);

    this.restClientService.uploadFile(this.currentFileUpload).subscribe(event =>{

      if(event.type==HttpEventType.UploadProgress){
        this.progress = Math.round(100*event.loaded/event.total)
        console.log("Erfolgreich");
      }else if (event instanceof HttpResponse){
       // this.timeStamp = Date.now();
      }
    }, error => {
      alert("Probleme de chargement" + "" + JSON.parse(error));
    })
  }
}
