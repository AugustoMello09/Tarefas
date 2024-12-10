package io.gitHub.AugustoMello09.tarefas.services;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.TarefaDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.TarefaRecord;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Tarefa;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Usuario;
import io.gitHub.AugustoMello09.tarefas.repositories.TarefaRepository;
import io.gitHub.AugustoMello09.tarefas.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.DataIntegratyViolationException;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarefaService {

	private final TarefaRepository repository;
	private final UsuarioRepository usuarioRepository;

	@Transactional(readOnly = true)
	public TarefaDTO findById(Long id) {
		Optional<Tarefa> obj = repository.findById(id);
		Tarefa entity = obj.orElseThrow(() -> new ObjectNotFoundException("tarefa não encontrada"));
		return new TarefaDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<TarefaDTO> listAll(UUID id) {
		List<Tarefa> tarefas = repository.findAllByUsuarioIdOrderByPosition(id);
		return tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).collect(Collectors.toList());
	}

	@Transactional
	public TarefaDTO create(TarefaRecord tarefaRecord, UUID id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("tarefa não encontrado"));
		nameAlreadyExists(tarefaRecord.name(), usuario.getId());
		Tarefa tarefa = new Tarefa();
		tarefa.setName(tarefaRecord.name());
		tarefa.setCost(tarefaRecord.cost());
		LocalDate dueDate = LocalDate.parse(tarefaRecord.dueDate());
		tarefa.setDueDate(dueDate);
		Integer maxPosition = repository.findMaxPositionByUsuario(id);
		tarefa.setPosition(maxPosition + 1);
		tarefa.setUsuario(usuario);
		repository.save(tarefa);
		return new TarefaDTO(tarefa);
	}

	@Transactional
	public void update(TarefaRecord tarefaRecord, Long id) {
		Tarefa tarefa = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("tarefa não encontrada"));
		nameAlreadyExists(tarefaRecord.name(), tarefa.getUsuario().getId());
		tarefa.setCost(tarefaRecord.cost());
		LocalDate dueDate = LocalDate.parse(tarefaRecord.dueDate());
		tarefa.setDueDate(dueDate);
		tarefa.setName(tarefaRecord.name());
		repository.save(tarefa);
	}

	@Transactional
	public void moveTarefa(UUID usuarioId, int sourceIndex, int destinationIndex) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ObjectNotFoundException("tarefa não encotrado"));
		List<Tarefa> tarefas = repository.findAllByUsuarioIdOrderByPosition(usuario.getId());
		Tarefa obj = tarefas.remove(sourceIndex);
		tarefas.add(destinationIndex, obj);
		int min = sourceIndex < destinationIndex ? sourceIndex : destinationIndex;
		int max = sourceIndex < destinationIndex ? destinationIndex : sourceIndex;
		for (int i = min; i <= max; i++) {
			Long tarefaId = tarefas.get(i).getId();
			repository.updateBelongingPosition(tarefaId, i, usuarioId);
		}

	}

	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}

	@Transactional
	public TarefaDTO patchUpdate(Map<String, Object> fields, Long id) {
		Tarefa tarefa = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Tarefa não encontrada"));
		if (fields.containsKey("name")) {
			String newName = (String) fields.get("name");
			nameAlreadyExists(newName, tarefa.getUsuario().getId());
		}
		merge(fields, tarefa);
		repository.save(tarefa);
		return new TarefaDTO(tarefa);
	}

	private void merge(Map<String, Object> fields, Tarefa tarefa) {
		fields.forEach((propertyName, propertyValue) -> {
			Field field = ReflectionUtils.findField(Tarefa.class, propertyName);
			if (field != null) {
				field.setAccessible(true);

				Object newValue = propertyValue;
				if (field.getType().equals(BigDecimal.class)) {
					newValue = new BigDecimal(propertyValue.toString());
				} else if (field.getType().equals(LocalDate.class)) {
					newValue = LocalDate.parse(propertyValue.toString());
				} else if (field.getType().equals(String.class)) {
					newValue = propertyValue.toString();
				}

				ReflectionUtils.setField(field, tarefa, newValue);
			}
		});
	}
	
	protected void nameAlreadyExists(String name, UUID idUsuario) {
		boolean exists = repository.existsByNameAndUsuarioId(name, idUsuario);
		if (exists) {
			throw new DataIntegratyViolationException("O usuário já possui uma tarefa com este nome.");
		}
	}

}
