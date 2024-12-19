import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { TarefaService } from 'src/app/core/service/tarefa.service';

@Component({
  selector: 'app-excluir-tarefa',
  templateUrl: './excluir-tarefa.component.html',
  styleUrls: ['./excluir-tarefa.component.css']
})
export class ExcluirTarefaComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ExcluirTarefaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: number },
    private service: TarefaService,
    private spinner: NgxSpinnerService,
    private snack: MatSnackBar) { }

  ngOnInit(): void {
  }

  public cancelar(): void {
    this.dialogRef.close();
  }

  public close(): void {
    this.dialogRef.close();
  }

  public deletar(): void {
    this.spinner.show();
    this.service.delete(this.data.id).subscribe(() => {
      this.dialogRef.close();
      this.spinner.hide();
      this.snack.open('Tarefa deletada com sucesso', 'Success', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 3000
      });
    }, () => {
      this.spinner.hide();
    });
  }


}
