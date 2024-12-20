import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TarefaRoutingModule } from './tarefa-routing.module';
import { TarefaComponent } from './tarefa.component';
import { SharedModule } from '../shared/shared.module';
import { TodasTarefasComponent } from './components/todas-tarefas/todas-tarefas.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { FavoritasTarefasComponent } from './components/favoritas-tarefas/favoritas-tarefas.component';


@NgModule({
  declarations: [
    TarefaComponent,
    TodasTarefasComponent,
    FavoritasTarefasComponent
  ],
  imports: [
    CommonModule,
    TarefaRoutingModule,
    SharedModule,
    DragDropModule
  ]
})
export class TarefaModule { }
