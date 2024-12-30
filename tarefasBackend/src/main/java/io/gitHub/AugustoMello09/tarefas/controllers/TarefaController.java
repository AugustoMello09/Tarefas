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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Tarefas")
@RestController
@RequestMapping(value = "/v1/tarefas")
@RequiredArgsConstructor
public class TarefaController {
	
	private final TarefaService service;
	
	@Operation(summary = "Busca uma tarefa por ID. ")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Lista todas as tarefas do usuário usando o ID. ")
	@GetMapping(value = "/minhasTarefas/{id}")
	public ResponseEntity<List<TarefaDTO>> listAll(@PathVariable UUID id){
		var response = service.listAll(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Cria uma tarefa passando o ID do usuário. ")
	@PostMapping(value = "/{id}")
	public ResponseEntity<TarefaDTO> create(@PathVariable UUID id,@Valid @RequestBody TarefaRecord tarefaRecord){
		var newObj = service.create(tarefaRecord, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newObj.getId())
				.toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@Operation(summary = "Atualiza uma tarefa. ")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody TarefaRecord tarefa,@PathVariable Long id){
		service.update(tarefa, id);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Deleta uma tarefa. ")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Habilita mover a tarefa dentro da lista trocando sua posição. ")
	@PostMapping(value = "/move/{id}")
	public ResponseEntity<Void> moveTarefa(@RequestBody MoveTarefaRecord moveDTO, @PathVariable UUID id) {
	    service.moveTarefa(id, moveDTO.sourceIndex(), moveDTO.destinationIndex());
	    return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Atualiza parcialmente uma Tarefa DTO.")
	@PatchMapping(value = "/{id}")
	public ResponseEntity<TarefaDTO> patchUpdate(@RequestBody Map<String, Object> fields, @PathVariable Long id) {
		var response = service.patchUpdate(fields, id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Defini uma tarefa como favorita.")
	@PatchMapping(value = "/DefinirTarefaFavorita/{id}")
	public ResponseEntity<TarefaDTO> favorite(@PathVariable Long id) {
		var response = service.activateFavorite(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Lista todas as tarefas favoritas do usuário.")
	@GetMapping(value = "/tarefasFavoritas/{id}")
	public ResponseEntity<List<TarefaDTO>> listFavoriteTesks(@PathVariable UUID id) {
		var response = service.getFavorites(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Lista todas as tarefas criadas no mesmo dia. ")
	@GetMapping(value = "/tarefasHoje/{id}")
	public ResponseEntity<List<TarefaDTO>> listTodayTasks(@PathVariable UUID id) {
		var response = service.getTodayTasks(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Lista todas as tarefas criadas na semana. ")
	@GetMapping(value = "/tarefasSemana/{id}")
	public ResponseEntity<List<TarefaDTO>> listWeeklyTasks(@PathVariable UUID id) {
		var response = service.getWeeklyTasks(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Lista todas as tarefas criadas no mês. ")
	@GetMapping(value = "/tarefasMes/{id}")
	public ResponseEntity<List<TarefaDTO>> listMonthlyTasks(@PathVariable UUID id) {
		var response = service.getMonthlyTasks(id);
		return ResponseEntity.ok().body(response);
	}
}
