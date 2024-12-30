package io.gitHub.AugustoMello09.tarefas.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.CargoDTO;
import io.gitHub.AugustoMello09.tarefas.services.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Cargo")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/cargo")
public class CargoController {
	
	private final CargoService service;
	
	@Operation(summary = "Busca um cargo por ID. ")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Cria um cargo. ")
	@PostMapping
	public ResponseEntity<CargoDTO> create(@Valid @RequestBody CargoDTO cargoDTO) {
		var newObj = service.create(cargoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newObj.getId())
				.toUri();
		return ResponseEntity.created(uri).body(newObj);
	}

}
