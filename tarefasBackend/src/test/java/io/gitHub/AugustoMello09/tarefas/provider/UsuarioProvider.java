package io.gitHub.AugustoMello09.tarefas.provider;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Cargo;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Usuario;

public class UsuarioProvider {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	
	private static final String NOME = "José";

	private static final String EMAIL = "meuEmail@gmail.com";

	private static final String SENHA = "123";
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UsuarioProvider(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	public Usuario criar() {
		Usuario usuario = new Usuario();
		usuario.setId(ID);
		usuario.setName(NOME);
		usuario.setEmail(EMAIL);
		usuario.setPassword(passwordEncoder.encode(SENHA));	
		Cargo cargo = new Cargo();
		cargo.setId(1L);
		usuario.getCargos().add(cargo);
		return usuario;
	}

}
