import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {RestService} from './services/rest.service';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { DocumentationComponent } from './components/documentation/documentation.component';
import { TestComponent } from './components/test/test.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'documentation', component: DocumentationComponent},
  {path: 'test/:ctx', component: TestComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    DocumentationComponent,
    TestComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes, {useHash: true}),
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [RestService],
  bootstrap: [AppComponent]
})
export class AppModule { }
