import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TarefaRoutingModule } from './tarefa-routing.module';
import { TarefaComponent } from './tarefa.component';
import { SharedModule } from '../shared/shared.module';
import { TodasTarefasComponent } from './components/todas-tarefas/todas-tarefas.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { FavoritasTarefasComponent } from './components/favoritas-tarefas/favoritas-tarefas.component';
import { HojeTarefasComponent } from './components/hoje-tarefas/hoje-tarefas.component';
import { SemanaTarefasComponent } from './components/semana-tarefas/semana-tarefas.component';
import { MesTarefasComponent } from './components/mes-tarefas/mes-tarefas.component';


@NgModule({
  declarations: [
    TarefaComponent,
    TodasTarefasComponent,
    FavoritasTarefasComponent,
    HojeTarefasComponent,
    SemanaTarefasComponent,
    MesTarefasComponent
  ],
  imports: [
    CommonModule,
    TarefaRoutingModule,
    SharedModule,
    DragDropModule
  ]
})
export class TarefaModule { }
