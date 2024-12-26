import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NavigationEnd, Router } from '@angular/router';
import { initFlowbite } from 'flowbite';
import { AuthService } from 'src/app/core/service/auth.service';
import { CalendarioComponent } from '../../components/calendario/calendario.component';
import { PerfilComponent } from '../../components/perfil/perfil.component';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  @Output() toggleView = new EventEmitter<string>();

  constructor(private auth: AuthService, private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        setTimeout(() => {  initFlowbite();})
      }
    });
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

}
