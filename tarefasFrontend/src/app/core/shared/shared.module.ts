import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterOneComponent } from './layout/footer-one/footer-one.component';
import { HeaderComponent } from './layout/header/header.component';
import { MaterialModule } from 'src/app/material.module';
import { FooterTwoComponent } from './layout/footer-two/footer-two.component';
import { CriarTarefaComponent } from './components/criar-tarefa/criar-tarefa.component';
import { FormsModule } from '@angular/forms';
import { ExcluirTarefaComponent } from './components/excluir-tarefa/excluir-tarefa.component';
import { UpdateTarefaComponent } from './components/update-tarefa/update-tarefa.component';



@NgModule({
  declarations: [
    FooterOneComponent,
    HeaderComponent,
    FooterTwoComponent,
    CriarTarefaComponent,
    ExcluirTarefaComponent,
    UpdateTarefaComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule
  ],
  exports: [
    FooterOneComponent,
    HeaderComponent,
    FooterTwoComponent
  ]
})
export class SharedModule { }
