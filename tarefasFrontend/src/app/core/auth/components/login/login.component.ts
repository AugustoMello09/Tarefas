import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from 'src/app/core/service/auth.service';
import { Login } from 'src/app/core/shared/model/login.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Output() toggle = new EventEmitter<void>();

  public usuario: Login = {
    email: '',
    senha: ''  
  }

  constructor(private snack: MatSnackBar,
    private authService: AuthService, private router: Router,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
  }

  toggleComponent() {
    this.toggle.emit();
  }

  public login(): void {
    this.spinner.show();
    this.authService.tentarLogar(this.usuario.email, this.usuario.senha).subscribe(res => {
      const token_access = JSON.stringify(res);
      localStorage.setItem('token_access', token_access);
      this.message("Login feito com sucesso!");
      this.spinner.hide();
      this.router.navigate(['/tarefas']);
    }, (err:  HttpErrorResponse) => {
      this.addMessageError(err);
      this.spinner.hide();
    })
  }

  public message(msg: string) {
    this.snack
      .open(`${msg}`, 'OK', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }

  public addMessageError(err: HttpErrorResponse) {
    if (err.status === 403) {
      this.message("Erro de authenticação. ");
    }
    if (err.status === 401) {
      this.message("Senha inválida. ");
    }
    if (err.status === 404) {
      this.message("Email não encontrado. ");
    }
  }

}
