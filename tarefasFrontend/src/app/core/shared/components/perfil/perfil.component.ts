import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { jwtDecode } from 'jwt-decode';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from 'src/app/core/service/auth.service';
import { Usuario } from '../../model/usuario.model';
import { UsuarioService } from 'src/app/core/service/usuario.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  public usuario: Usuario = {
    id: '',
    name: '',
    email: '',
    notification: false || true
  }

    constructor(public dialogRef: MatDialogRef<PerfilComponent>,
      private spinner: NgxSpinnerService,
      private snack: MatSnackBar,
      private auth: AuthService,
      private service: UsuarioService) {
    }

  ngOnInit(): void {
    this.findById();
  }

  public cancelar(): void {
    this.dialogRef.close();
  }

  public close(): void {
    this.dialogRef.close();
  }

  public findById(): void {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      const id = decodedToken.id;
      this.service.findById(id).subscribe(result => {
        this.usuario = result;
      })
    }
  }

  public onNotificationToggle(): void {
    this.spinner.show();
    this.service.activateNotification(this.usuario.id).subscribe({
      next: () => {
        this.spinner.hide();
        this.snack.open('Notifição status modificado com sucesso', 'Success', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 3000
        });
      },
      error: () => {
        this.spinner.hide();
        this.usuario.notification = !this.usuario.notification;
        this.snack.open('Erro ao ativar notificação', 'Error', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 3000
        });
      }
    });
  }

}
