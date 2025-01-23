import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from 'src/app/core/service/auth.service';
import { UsuarioService } from 'src/app/core/service/usuario.service';
import { UsuarioRegistro } from 'src/app/core/shared/model/usuarioRegistro.model';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {

  @Output() toggle = new EventEmitter<void>();

  public usuario: UsuarioRegistro = {
    id: '',
    name: '',
    email: '',
    senha: '',
    cargos: [
      { id: 1 } 
    ]
  }

  constructor(private snack: MatSnackBar, private service: UsuarioService,
      private authService: AuthService, private router: Router,
      private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
  }

  toggleComponent() {
    this.toggle.emit();
  }

  public criar() {
    this.spinner.show();
    this.service.create(this.usuario).subscribe(() => {
      this.message("Conta criada com sucesso. ");
      this.authService.tentarLogar(this.usuario.email, this.usuario.senha).subscribe(result => {
        const token_access = JSON.stringify(result);
        localStorage.setItem('token_access', token_access);
        this.spinner.hide();
        this.router.navigate(['/tarefas']);
      })
    }, (err: HttpErrorResponse) => {
      this.spinner.hide();
      if (err.status === 400) {
          this.message('Este email já está em uso. Escolha outro.');
        } 
    });
  }

  public addMessageError(fieldName: string, errorMessage: string): void {
    this.message(`${fieldName}: ${errorMessage}`);
  }

  public message(msg: string): void {
    this.snack
      .open(`${msg}`, 'OK', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }

}
