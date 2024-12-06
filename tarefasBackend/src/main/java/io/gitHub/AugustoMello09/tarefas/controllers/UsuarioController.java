package io.gitHub.AugustoMello09.tarefas.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.UsuarioDTOInsert;
import io.gitHub.AugustoMello09.tarefas.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id){
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTOInsert usuarioDTO) {
		var newObj = service.create(usuarioDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody UsuarioDTO usuarioDTO, @PathVariable UUID id) {
		service.updateUser(usuarioDTO, id);
		return ResponseEntity.ok().build();
	}
}
