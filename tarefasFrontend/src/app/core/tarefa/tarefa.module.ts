import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TarefaRoutingModule } from './tarefa-routing.module';
import { TarefaComponent } from './tarefa.component';
import { SharedModule } from '../shared/shared.module';
import { TodasTarefasComponent } from './components/todas-tarefas/todas-tarefas.component';
import { DragDropModule } from '@angular/cdk/drag-drop';


@NgModule({
  declarations: [
    TarefaComponent,
    TodasTarefasComponent
  ],
  imports: [
    CommonModule,
    TarefaRoutingModule,
    SharedModule,
    DragDropModule
  ]
})
export class TarefaModule { }
