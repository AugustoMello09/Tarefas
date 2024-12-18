import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: '', loadChildren: () => import('./core/home/home.module').then(m => m.HomeModule) },
  { path: 'auth', loadChildren: () => import('./core/auth/auth.module').then(m => m.AuthModule) },
  {
    path: 'tarefas', loadChildren: () => import('./core/tarefa/tarefa.module').then(m => m.TarefaModule),
    canActivate: [AuthGuard]
   }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
