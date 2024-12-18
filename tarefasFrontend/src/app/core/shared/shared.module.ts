import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterOneComponent } from './layout/footer-one/footer-one.component';
import { HeaderComponent } from './layout/header/header.component';
import { MaterialModule } from 'src/app/material.module';
import { FooterTwoComponent } from './layout/footer-two/footer-two.component';



@NgModule({
  declarations: [
    FooterOneComponent,
    HeaderComponent,
    FooterTwoComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    FooterOneComponent,
    HeaderComponent,
    FooterTwoComponent
  ]
})
export class SharedModule { }
