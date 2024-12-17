import { Component, EventEmitter, OnInit, Output } from '@angular/core';
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
    password: '',
    cargos: [
      { id: 1 } 
    ]
  }

  constructor() { }

  ngOnInit(): void {
  }

  toggleComponent() {
    this.toggle.emit();
  }

}
