package io.gitHub.AugustoMello09.tarefas.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.MoveTarefaRecord;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.TarefaDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.TarefaRecord;
import io.gitHub.AugustoMello09.tarefas.services.TarefaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/tarefas")
@RequiredArgsConstructor
public class TarefaController {
	
	private final TarefaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/minhasTarefas/{id}")
	public ResponseEntity<List<TarefaDTO>> listAll(@PathVariable UUID id){
		var response = service.listAll(id);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<TarefaDTO> create(@PathVariable UUID id, @RequestBody TarefaRecord tarefaRecord){
		var newObj = service.create(tarefaRecord, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newObj.getId())
				.toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody TarefaRecord tarefa,@PathVariable Long id){
		service.update(tarefa, id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/move/{id}")
	public ResponseEntity<Void> moveTarefa(@RequestBody MoveTarefaRecord moveDTO, @PathVariable UUID id) {
	    service.moveTarefa(id, moveDTO.sourceIndex(), moveDTO.destinationIndex());
	    return ResponseEntity.ok().build();
	}
	
	@PatchMapping(value = "/{id}")
	public ResponseEntity<TarefaDTO> patchUpdate(@RequestBody Map<String, Object> fields, @PathVariable Long id) {
		var response = service.patchUpdate(fields, id);
		return ResponseEntity.ok().body(response);
	}
}
