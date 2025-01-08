import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UsuarioRegistro } from '../shared/model/usuarioRegistro.model';
import { Usuario } from '../shared/model/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  baseUrl: string = environment.baseUrl;

  private usuarioSubject = new BehaviorSubject<Usuario | null>(null);

  constructor(private http: HttpClient) { }

  get usuario$(): Observable<Usuario | null> {
    return this.usuarioSubject.asObservable();
  }
  
  emitUsuarioUpdate(usuario: Usuario): void {
    this.usuarioSubject.next(usuario);
  }

  public create(usuario: UsuarioRegistro): Observable<UsuarioRegistro> {
    const url = `${this.baseUrl}/v1/usuarios`;
    return this.http.post<UsuarioRegistro>(url, usuario);
  }

  public findById(id: any): Observable<Usuario> {
    const url = `${this.baseUrl}/v1/usuarios/${id}`;
    return this.http.get<Usuario>(url);
  }

  public activateNotification(id: any): Observable<Usuario> {
    const url = `${this.baseUrl}/v1/usuarios/ativarNotificacao/${id}`;
    return this.http.patch<Usuario>(url, {});
  }

  public uploadImage(usuarioId: number, image: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('imagem', image);
    return this.http.patch(`${this.baseUrl}/v1/usuarios/${usuarioId}/img`, formData, {
      responseType: 'text'
    });
  }
}
