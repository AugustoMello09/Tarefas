import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UsuarioRegistro } from '../shared/model/usuarioRegistro.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public create(usuario: UsuarioRegistro): Observable<UsuarioRegistro> {
    const url = `${this.baseUrl}/v1/usuarios`;
    return this.http.post<UsuarioRegistro>(url, usuario);
  }
}
