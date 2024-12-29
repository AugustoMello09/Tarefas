import { DialogRef } from '@angular/cdk/dialog';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { jwtDecode } from 'jwt-decode';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from 'src/app/core/service/auth.service';
import { UsuarioService } from 'src/app/core/service/usuario.service';
import { Usuario } from '../../model/usuario.model';

@Component({
  selector: 'app-notificacao',
  templateUrl: './notificacao.component.html',
  styleUrls: ['./notificacao.component.css']
})
export class NotificacaoComponent implements OnInit {

  public usuario: Usuario = {
    id: '',
    email: '',
    name: '',
    notification: false || true,
    imgUrl: ''
  }

  constructor(private service: UsuarioService,
    private auth: AuthService,
    private spinner: NgxSpinnerService,
    private snack: MatSnackBar,
    private dialog: DialogRef
  ) { }

  ngOnInit(): void {
  }

  public cancelar(): void {
    this.dialog.close();
  }

  public close(): void {
    this.dialog.close();
  }

  public ativar(): void {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      const id = decodedToken.id;
      this.spinner.show();
      this.service.findById(id).subscribe(result => {
        this.usuario = result;
          this.service.activateNotification(this.usuario.id).subscribe(() => {
          this.spinner.hide();
          this.snack.open('Notificação ativa com sucesso', 'Success', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 3000
          });
          this.dialog.close();
        }, () => {
          this.spinner.hide();
        });
      }, () => {
        this.spinner.hide();
      })
    }
  }

}
