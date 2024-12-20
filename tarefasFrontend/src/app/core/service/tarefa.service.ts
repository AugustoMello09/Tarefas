import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Tarefa } from '../shared/model/tarefa.model';
import { HttpClient } from '@angular/common/http';
import { TarefaInsert } from '../shared/model/tarefaInsert.model';

@Injectable({
  providedIn: 'root'
})
export class TarefaService {

  baseUrl: string = environment.baseUrl;

  private tarefasSubject: BehaviorSubject<Tarefa[]> = new BehaviorSubject<Tarefa[]>([]);
  public tarefas$: Observable<Tarefa[]> = this.tarefasSubject.asObservable();

  constructor(private http: HttpClient) { }

  public listAll(id : any): void {
    const url = `${this.baseUrl}/v1/tarefas/minhasTarefas/${id}`;
    this.http.get<Tarefa[]>(url).subscribe((tarefas) => {
      this.tarefasSubject.next(tarefas); 
    });
  }

  public favorite(id: number): Observable<void> {
    const url = `${this.baseUrl}/v1/tarefas/DefinirTarefaFavorita/${id}`;
    return this.http.patch<void>(url, {}); 
  } 

  public create(tarefa: any, id: any): Observable<TarefaInsert> {
    const url = `${this.baseUrl}/v1/tarefas/${id}`;
    return this.http.post<Tarefa>(url, tarefa).pipe(
      tap((novaTarefa) => {
        this.tarefasSubject.next([...this.tarefasSubject.value, novaTarefa]);
      })
    );
  }

  public delete(id: any): Observable<void>{
    const url = `${this.baseUrl}/v1/tarefas/${id}`;
    return this.http.delete<void>(url).pipe(
      tap(() => {
        const tarefas = this.tarefasSubject.value.filter((tarefa) => tarefa.id !== id);
        this.tarefasSubject.next(tarefas);
      })
    );
  }

  public findById(id: any): Observable<Tarefa>{
    const url = `${this.baseUrl}/v1/tarefas/${id}`;
    return this.http.get<Tarefa>(url);
  }

  public patchUpdate(id: number, fields: any): Observable<TarefaInsert> {
    const url = `${this.baseUrl}/v1/tarefas/${id}`;
    return this.http.patch<Tarefa>(url, fields).pipe(
      tap((tarefaAtualizada) => {
        const tarefas = this.tarefasSubject.value.map((tarefa) =>
          tarefa.id === id ? { ...tarefa, ...tarefaAtualizada } : tarefa
        );
        this.tarefasSubject.next(tarefas); 
      })
    );
  }

  public updateTaskOrder(order: { sourceIndex: number, destinationIndex: number }, id : any): Observable<void> {
    const url = `${this.baseUrl}/v1/tarefas/move/${id}`;
    return this.http.post<void>(url, order);
  }

  public listAllFavorite(id : any): void {
    const url = `${this.baseUrl}/v1/tarefas/tarefasFavoritas/${id}`;
    this.http.get<Tarefa[]>(url).subscribe((tarefas) => {
      this.tarefasSubject.next(tarefas); 
    });
  }

  public listAllHoje(id : any): void {
    const url = `${this.baseUrl}/v1/tarefas/tarefasHoje/${id}`;
    this.http.get<Tarefa[]>(url).subscribe((tarefas) => {
      this.tarefasSubject.next(tarefas); 
    });
  }

  public listAllSemana(id : any): void {
    const url = `${this.baseUrl}/v1/tarefas/tarefasSemana/${id}`;
    this.http.get<Tarefa[]>(url).subscribe((tarefas) => {
      this.tarefasSubject.next(tarefas); 
    });
  }

  public listAllMes(id : any): void {
    const url = `${this.baseUrl}/v1/tarefas/tarefasMes/${id}`;
    this.http.get<Tarefa[]>(url).subscribe((tarefas) => {
      this.tarefasSubject.next(tarefas); 
    });
  }
}
