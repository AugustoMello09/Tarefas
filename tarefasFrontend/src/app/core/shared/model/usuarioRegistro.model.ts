import { CargoId } from "./cargoId.model";

export interface UsuarioRegistro {
  id: any;
  name: string;
  email: string;
  senha: string;
  cargos: CargoId[];
}