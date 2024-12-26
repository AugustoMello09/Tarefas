import { Component, OnInit } from '@angular/core';
import { Login } from '../shared/model/login.model';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  showLoginComponent: boolean = true;

  constructor() { }

  ngOnInit(): void {
    localStorage.removeItem('token_access');
  }

  toggleComponent() {
    this.showLoginComponent = !this.showLoginComponent;
  }

}
