package io.gitHub.AugustoMello09.tarefas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.AuthenticationDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.MensagemDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.RefreshTokenDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.TokenResponseDTO;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Usuario;
import io.gitHub.AugustoMello09.tarefas.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth")
@RestController
@RequestMapping(value = "/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private final TokenService tokenService;
	
	@Operation(summary = "Login da aplicação. ")
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDTO data) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
			var authentication = authenticationManager.authenticate(usernamePassword);
			var tokens = tokenService.generateTokens((Usuario) authentication.getPrincipal());
			return ResponseEntity.ok().body(new TokenResponseDTO(tokens.accessToken(), tokens.refreshToken()));
		} catch (InternalAuthenticationServiceException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDTO("Email não encontrado"));
	    } catch (BadCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensagemDTO("Senha inválida"));
	    }
	}
	
	@Operation(summary = "Refresh token da aplicação. ")
	@PostMapping(value = "/refresh-token")
	public ResponseEntity<TokenResponseDTO> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDto) {
		TokenResponseDTO tokens = tokenService.refreshAccessToken(refreshTokenDto.token());
		return ResponseEntity.ok(tokens);
	}
}
