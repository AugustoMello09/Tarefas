import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { jwtDecode } from 'jwt-decode';
import { AuthService } from 'src/app/core/service/auth.service';
import { TarefaService } from 'src/app/core/service/tarefa.service';
import { CriarTarefaComponent } from 'src/app/core/shared/components/criar-tarefa/criar-tarefa.component';
import { ExcluirTarefaComponent } from 'src/app/core/shared/components/excluir-tarefa/excluir-tarefa.component';
import { UpdateTarefaComponent } from 'src/app/core/shared/components/update-tarefa/update-tarefa.component';
import { Tarefa } from 'src/app/core/shared/model/tarefa.model';

@Component({
  selector: 'app-todas-tarefas',
  templateUrl: './todas-tarefas.component.html',
  styleUrls: ['./todas-tarefas.component.css']
})
export class TodasTarefasComponent implements OnInit {

  public tarefas: Tarefa[] = [];

  public dragging: boolean = false;
  public draggedIndex: number | null = null;

  constructor(private service: TarefaService,
    private auth: AuthService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      const id = decodedToken.id;
      this.service.listAll(id);
      this.service.tarefas$.subscribe((tarefas) => { 
      this.tarefas = tarefas;
      })
    }
  }
  
  public favorite(id: number): void {
    this.service.favorite(id).subscribe(() => {
      const tarefa = this.tarefas.find(t => t.id === id);
      if (tarefa) {
        tarefa.favorite = !tarefa.favorite;
      }
    }); 
  }

  public open(): void {
    this.dialog.open(CriarTarefaComponent);
  }

  public deleteTarefa(id: number): void {
    this.dialog.open(ExcluirTarefaComponent, {
      data: { id: id }
    });
  }

  public atualizarTarefa(id: number): void {
    this.dialog.open(UpdateTarefaComponent, { data: { id: id } })
      .afterClosed().subscribe((updatedTask: Tarefa | null) => {
        if (updatedTask) {
          this.service.patchUpdate(id, updatedTask); 
        }
      });
  }

  public onDragStarted(index: number): void {
    this.dragging = true;
    this.draggedIndex = index;
  }
  public onDragEnded(): void {
    this.dragging = false;
    this.draggedIndex = null;
  }

  public drop(event: CdkDragDrop<Tarefa[]>): void {
    this.onDragEnded();
    
    const sourceIndex = event.previousIndex;
    const destinationIndex = event.currentIndex;
    
    moveItemInArray(this.tarefas, sourceIndex, destinationIndex);

    const orderUpdate = {
      sourceIndex: sourceIndex,
      destinationIndex: destinationIndex
    };

    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      const id = decodedToken.id;

      this.service.updateTaskOrder(orderUpdate, id).subscribe(() => {
      }, () => {
        moveItemInArray(this.tarefas, destinationIndex, sourceIndex);
      });
    }

  }

  public moveTarefa(sourceIndex: number, destinationIndex: number): void {
    moveItemInArray(this.tarefas, sourceIndex, destinationIndex);
    const orderUpdate = { sourceIndex: sourceIndex, destinationIndex: destinationIndex };
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      const id = decodedToken.id;
      this.service.updateTaskOrder(orderUpdate, id).subscribe(() => {
      }, () => {
        moveItemInArray(this.tarefas, destinationIndex, sourceIndex);
      });
    } 
  }

}
