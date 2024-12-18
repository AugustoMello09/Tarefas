import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TarefaRoutingModule } from './tarefa-routing.module';
import { TarefaComponent } from './tarefa.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    TarefaComponent
  ],
  imports: [
    CommonModule,
    TarefaRoutingModule,
    SharedModule
  ]
})
export class TarefaModule { }
