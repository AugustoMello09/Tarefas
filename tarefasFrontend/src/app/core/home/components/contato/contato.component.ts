import { Component, OnInit } from '@angular/core';
import { EmailModel } from 'src/app/core/shared/model/email.model';

@Component({
  selector: 'app-contato',
  templateUrl: './contato.component.html',
  styleUrls: ['./contato.component.css']
})
export class ContatoComponent implements OnInit {

  emailModel : EmailModel = {
    name: '',
    email: '',
    message : '',
  }

  constructor() { }

  ngOnInit(): void {
  }

}
