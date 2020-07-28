import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {RestService} from './services/rest.service';

import {AppComponent} from './app.component';
import {HomeComponent} from './components/home/home.component';
import {DocumentationComponent} from './components/documentation/documentation.component';
import {TestComponent} from './components/test/test.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    DocumentationComponent,
    TestComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule
  ],
  providers: [RestService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
