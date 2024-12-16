import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { FuncionalidadesComponent } from './components/funcionalidades/funcionalidades.component';
import { VenhaComponent } from './components/venha/venha.component';


@NgModule({
  declarations: [
    HomeComponent,
    InicioComponent,
    FuncionalidadesComponent,
    VenhaComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule
  ]
})
export class HomeModule { }
