import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tarefa',
  templateUrl: './tarefa.component.html',
  styleUrls: ['./tarefa.component.css']
})
export class TarefaComponent implements OnInit {

  showTarefaComponent: boolean = true;

  constructor() { }

  ngOnInit(): void {
  }

  toggleComponent() {
    this.showTarefaComponent = !this.showTarefaComponent;
  }



}
