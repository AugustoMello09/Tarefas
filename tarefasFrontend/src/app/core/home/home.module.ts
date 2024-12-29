import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { FuncionalidadesComponent } from './components/funcionalidades/funcionalidades.component';
import { VenhaComponent } from './components/venha/venha.component';
import { SabemosComponent } from './components/sabemos/sabemos.component';
import { ContatoComponent } from './components/contato/contato.component';
import { SharedModule } from '../shared/shared.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    HomeComponent,
    InicioComponent,
    FuncionalidadesComponent,
    VenhaComponent,
    SabemosComponent,
    ContatoComponent,
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class HomeModule { }
