import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NavigationEnd, Router } from '@angular/router';
import { initFlowbite } from 'flowbite';
import { AuthService } from 'src/app/core/service/auth.service';
import { CalendarioComponent } from '../../components/calendario/calendario.component';
import { PerfilComponent } from '../../components/perfil/perfil.component';
import { jwtDecode } from 'jwt-decode';
import { Usuario } from '../../model/usuario.model';
import { UsuarioService } from 'src/app/core/service/usuario.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  public usuario: Usuario = {
      id: '',
      name: '',
      email: '',
      notification: false || true,
      imgUrl: ''
  }

  @Output() toggleView = new EventEmitter<string>();

  constructor(private auth: AuthService, private router: Router, private dialog: MatDialog,
    private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    this.usuarioService.usuario$.subscribe(updatedUsuario => {
      if (updatedUsuario) {
        this.usuario = updatedUsuario;
      }
    });
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        setTimeout(() => {  initFlowbite();})
      }
    });
    this.find();
  }

  showView(view: string) {
    this.toggleView.emit(view);
  }

  public sair(): void {
    this.auth.logout(); 
    this.router.navigate(['']);
  }

  public inicio(): void {
    this.router.navigate(['']);
  }

  public calendar(): void {
    this.dialog.open(CalendarioComponent);
  }

  public perfil(): void {
    this.dialog.open(PerfilComponent);
  }

  public find(): void {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      const id = decodedToken.id;
      this.usuarioService.findById(id).subscribe(result => {
        this.usuario = result;
        this.usuarioService.emitUsuarioUpdate(result);
      })
    }
  }  

}
