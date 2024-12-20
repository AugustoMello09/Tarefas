import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tarefa',
  templateUrl: './tarefa.component.html',
  styleUrls: ['./tarefa.component.css']
})
export class TarefaComponent implements OnInit {

  currentView: string = 'todas';

  constructor() { }

  ngOnInit(): void {
  }

  toggleComponent(view: string) {
    this.currentView = view;
  }
}
