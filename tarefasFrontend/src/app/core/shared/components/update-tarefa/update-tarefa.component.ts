import { Component, Inject, OnInit } from '@angular/core';
import { TarefaInsert } from '../../model/tarefaInsert.model';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TarefaService } from 'src/app/core/service/tarefa.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-update-tarefa',
  templateUrl: './update-tarefa.component.html',
  styleUrls: ['./update-tarefa.component.css']
})
export class UpdateTarefaComponent implements OnInit {

  public tarefa: TarefaInsert = {
    name: '',
    cost: 0,
    dueDate: ''
  }
 
  private originalTarefa: TarefaInsert = {
    name: '',
    cost: 0,
    dueDate: ''
  }

  constructor(public dialogRef: MatDialogRef<UpdateTarefaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: number },
    private service: TarefaService,
    private snack: MatSnackBar,
    private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.findById();
  }

  public findById(): void {
    this.spinner.show();
    this.service.findById(this.data.id).subscribe(res => {
      this.tarefa = res;
      this.originalTarefa = JSON.parse(JSON.stringify(res));
      this.spinner.hide();
    }, () => {
      this.tarefa = { name: '', cost: 0, dueDate: '' };
      this.spinner.hide();
    });
  }

  public atualiza() {
    if (!this.tarefa || !this.originalTarefa) {
      return;
    }
  
    const fieldsToUpdate: Partial<TarefaInsert> = {};
  
    if (this.tarefa.name !== this.originalTarefa.name) {
      fieldsToUpdate.name = this.tarefa.name;
    }
    if (this.tarefa.dueDate !== this.originalTarefa.dueDate) {
      fieldsToUpdate.dueDate = this.tarefa.dueDate;
    }
    if (this.tarefa.cost !== this.originalTarefa.cost) {
      fieldsToUpdate.cost = this.tarefa.cost;
    }
  
    if (Object.keys(fieldsToUpdate).length === 0) {
      this.dialogRef.close();
    }

    if (Object.keys(fieldsToUpdate).length > 0) {
      this.spinner.show();
      this.service.patchUpdate(this.data.id, fieldsToUpdate).subscribe(() => {
        this.snack.open('Tarefa atualizada com sucesso', 'Success', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 3000
        });
        this.spinner.hide();
        this.dialogRef.close(true);
      }, (err) => {
        this.addMessageError(err);
        this.spinner.hide();
      });
    } 
  }


  public message(msg: string): void {
    this.snack
      .open(`${msg}`, 'Error', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }

  public addMessageError(err: HttpErrorResponse) {
    if (err.status === 400) {
      this.message("Não é possível atualizar uma tarefa com o nome já existente ");
    }
  }

}
