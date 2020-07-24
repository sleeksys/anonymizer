import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {UploadFileComponent} from './components/upload-file/upload-file.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '', redirectTo:'products/1/0', pathMatch:'full'},
  {path: 'upload', component: UploadFileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
