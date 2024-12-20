import { Component, OnInit } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { AuthService } from 'src/app/core/service/auth.service';
import { TarefaService } from 'src/app/core/service/tarefa.service';
import { Tarefa } from 'src/app/core/shared/model/tarefa.model';

@Component({
  selector: 'app-hoje-tarefas',
  templateUrl: './hoje-tarefas.component.html',
  styleUrls: ['./hoje-tarefas.component.css']
})
export class HojeTarefasComponent implements OnInit {

  public tarefas: Tarefa[] = [];
  
    constructor(private service: TarefaService,
        private auth: AuthService
      ) { }
  
    ngOnInit(): void {
    const token = this.auth.obterToken();
        if (token) {
          const decodedToken: any = jwtDecode(token);
          const id = decodedToken.id;
          this.service.listAllHoje(id);
          this.service.tarefas$.subscribe((tarefas) => { 
          this.tarefas = tarefas;
          })
        }  
    }

}
