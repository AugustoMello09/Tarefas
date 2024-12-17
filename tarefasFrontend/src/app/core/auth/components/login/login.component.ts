import { Component, EventEmitter, OnInit, Output } from '@angular/core';
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

  constructor() { }

  ngOnInit(): void {
  }

  toggleComponent() {
    this.toggle.emit();
  }

}
