package io.gitHub.AugustoMello09.tarefas.services;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Usuario;
import io.gitHub.AugustoMello09.tarefas.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.AuthorizationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UsuarioRepository usuarioRepository;
	
	public Usuario authenticated() {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return usuarioRepository.findByEmail(username).get();
		} catch (Exception e) {
			throw new AuthorizationException("Usuário inválido");
		}
	}
	
	public void validateSelfOrAdmin(UUID Idusuario) {
		Usuario usuario = authenticated();
		if (Idusuario != null && !usuario.getId().equals(Idusuario) && !usuario.hasCargo("ROLE_ADM")) {
			throw new AuthorizationException("Acesso negado");
		} else if (Idusuario == null && !usuario.hasCargo("ROLE_ADM")) {
			throw new AuthorizationException("Acesso negado");
		}
	}
	
	public boolean isAuthenticated() {
		try {
			authenticated();
			return true;
		} catch (AuthorizationException e) {
			return false;
		}
	}
}
