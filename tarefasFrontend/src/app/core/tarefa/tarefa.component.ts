import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { MatDialog } from '@angular/material/dialog';
import { NotificacaoComponent } from '../shared/components/notificacao/notificacao.component';
import { UsuarioService } from '../service/usuario.service';
import { Usuario } from '../shared/model/usuario.model';

@Component({
  selector: 'app-tarefa',
  templateUrl: './tarefa.component.html',
  styleUrls: ['./tarefa.component.css']
})
export class TarefaComponent implements OnInit {

  currentView: string = 'todas';

  public usuario: Usuario = {
      id: '',
      email: '',
      name: '',
      notification: false || true,
      imgUrl: ''
  }

  constructor(private auth : AuthService, private dialog: MatDialog, private usuarioService : UsuarioService) { }

  ngOnInit(): void {
    this.verificar();
  }

  toggleComponent(view: string) {
    this.currentView = view;
  }

  public verificar(): void {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      const id = decodedToken.id;
      this.usuarioService.findById(id).subscribe(resp => {
        
        this.usuario = resp;
        const notificationStatus = this.usuario.notification != true;
        
        if (notificationStatus) {
          this.dialog.open(NotificacaoComponent);
        }
      })
    }
  }
}
