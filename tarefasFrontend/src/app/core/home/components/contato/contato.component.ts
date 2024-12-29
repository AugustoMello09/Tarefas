import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EmailModel } from 'src/app/core/shared/model/email.model';
import * as EmailJS from '@emailjs/browser';

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

  constructor(private snack: MatSnackBar) { }

  ngOnInit(): void {
  }

  public enviarEmail() {
    const templateParams = {
      name: this.emailModel.name,
      email: this.emailModel.email,
      message: this.emailModel.message
    };
    EmailJS.send('service_8nhuv4p', 'template_clxzylj', templateParams)
      .then(() => {
        this.message("Mensagem enviada com sucesso!");
      }, () => {
        alert('Erro ao enviar email. Tente novamente.');
      });
  }
  public message(msg: string) {
    this.snack
      .open(`${msg}`, 'OK', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }

}
