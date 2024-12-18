import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatSnackBarModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
  ],
  exports: [
    MatSnackBarModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
  ]
})
export class MaterialModule { }
