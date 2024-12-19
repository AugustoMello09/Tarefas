import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from 'src/app/core/service/auth.service';
import { TarefaService } from 'src/app/core/service/tarefa.service';
import { TarefaInsert } from '../../model/tarefaInsert.model';
import { HttpErrorResponse } from '@angular/common/http';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-criar-tarefa',
  templateUrl: './criar-tarefa.component.html',
  styleUrls: ['./criar-tarefa.component.css']
})
export class CriarTarefaComponent implements OnInit {

  public tarefa: TarefaInsert = {
    name: '',
    cost: 0,
    dueDate: ''
  }

  constructor(private service: TarefaService,
    private auth : AuthService,
    private dialogRef: MatDialogRef<CriarTarefaComponent>,
    private snack: MatSnackBar,
    private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
  }

  public create(): void {
    this.spinner.show();
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      const id = decodedToken.id;
        this.service.create(this.tarefa, id).subscribe(() => {
        this.dialogRef.close();
        this.spinner.hide();
      }, (err: HttpErrorResponse) => { 
        this.addMessageError(err);
        this.spinner.hide();
      })
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
      this.message("Não é possível criar uma tarefa com o nome já existente ");
    }
  }



}
