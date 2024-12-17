import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterOneComponent } from './layout/footer-one/footer-one.component';



@NgModule({
  declarations: [
    FooterOneComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    FooterOneComponent
  ]
})
export class SharedModule { }
