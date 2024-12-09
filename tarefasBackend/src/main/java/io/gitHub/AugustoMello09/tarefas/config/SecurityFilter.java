package io.gitHub.AugustoMello09.tarefas.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Usuario;
import io.gitHub.AugustoMello09.tarefas.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.tarefas.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
	
	private final UsuarioRepository usuarioRepository;
	
	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token;
		String authorizationHeader = request.getHeader("Authorization") == null ? request.getParameter("Authorization")
				: request.getHeader("Authorization");
		try {
			if (authorizationHeader != null) {
				token = authorizationHeader.replace("Bearer ", "");
				String email = tokenService.validacaoToken(token);
				Usuario usuario = usuarioRepository.findByEmail(email).get();
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,
						null, usuario.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		} catch (TokenExpiredException ex) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Token expirado. Faça login novamente.");
		}
	}

}
