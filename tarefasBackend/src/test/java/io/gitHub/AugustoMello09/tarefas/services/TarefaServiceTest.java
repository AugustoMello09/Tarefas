package io.gitHub.AugustoMello09.tarefas.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.TarefaDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.TarefaRecord;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Tarefa;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Usuario;
import io.gitHub.AugustoMello09.tarefas.provider.TarefaProvider;
import io.gitHub.AugustoMello09.tarefas.provider.UsuarioProvider;
import io.gitHub.AugustoMello09.tarefas.repositories.TarefaRepository;
import io.gitHub.AugustoMello09.tarefas.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.DataIntegratyViolationException;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

	private static final long ID = 1L;
	private static final UUID IDUSUARIO = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	@InjectMocks
	private TarefaService service;

	@Mock
	private TarefaRepository repository;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@Mock
	private EmailService emailService;

	private TarefaProvider tarefaProvider;
	private UsuarioProvider usuarioProvider;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		tarefaProvider = new TarefaProvider();
		usuarioProvider = new UsuarioProvider(passwordEncoder);
		service = new TarefaService(repository, usuarioRepository, emailService);
	}

	@DisplayName("Deve retornar uma tarefa com sucesso.")
	@Test
	public void shouldReturnATarefaWithSuccess() {
		Tarefa tarefa = tarefaProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(tarefa));

		var response = service.findById(ID);
		assertNotNull(response);
		assertEquals(TarefaDTO.class, response.getClass());
		assertEquals(ID, response.getId());
	}

	@DisplayName("Deve retornar uma tarefa não encontrado.")
	@Test
	public void shouldReturnTarefaNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
	}

	@DisplayName("Deve retornar uma lista de tarefas.")
	@Test
	public void whenFindAllThenReturnTarefaDTO() {
		;
		List<Tarefa> tarefas = Arrays
				.asList(new Tarefa(ID, "Estudar", BigDecimal.ZERO, LocalDate.now().plusDays(2), 0, false, null));
		when(repository.findAllByUsuarioIdOrderByPosition(IDUSUARIO)).thenReturn(tarefas);
		List<TarefaDTO> result = service.listAll(IDUSUARIO);
		assertNotNull(result);
	}

	@DisplayName("Deve criar uma tarefa com sucesso.")
	@Test
	public void whenCreateThenReturnTarefaDTO() {
		Usuario usuario = usuarioProvider.criar();

		TarefaRecord tarefaRecord = new TarefaRecord("Nome da Tarefa", new BigDecimal("100.00"), "2023-12-31");

		Tarefa tarefa = new Tarefa();
		tarefa.setId(1L);
		tarefa.setName(tarefaRecord.name());
		tarefa.setCost(tarefaRecord.cost());
		tarefa.setDueDate(LocalDate.parse(tarefaRecord.dueDate()));
		tarefa.setPosition(1);

		when(usuarioRepository.findById(IDUSUARIO)).thenReturn(Optional.of(usuario));
		when(repository.save(any(Tarefa.class))).thenReturn(tarefa);
		when(repository.findMaxPositionByUsuario(IDUSUARIO)).thenReturn(0);

		TarefaDTO resultado = service.create(tarefaRecord, IDUSUARIO);

		assertNotNull(resultado);
		assertEquals(tarefa.getName(), resultado.getName());
		assertEquals(tarefa.getCost(), resultado.getCost());
		assertEquals(tarefa.getDueDate(), resultado.getDueDate());
		assertEquals(tarefa.getPosition(), resultado.getPosition());

		verify(repository, times(1)).findMaxPositionByUsuario(IDUSUARIO);
	}

	@DisplayName("Deve retornar usuário não encontrado ao tentar criar uma tarefa.")
	@Test
	public void whenCreateThenReturnUsuarioNotFound() {
		TarefaRecord tarefaRecord = new TarefaRecord("Nome da Tarefa", new BigDecimal("100.00"), "2023-12-31");
		when(usuarioRepository.findById(IDUSUARIO)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.create(tarefaRecord, IDUSUARIO);
		});
		assertEquals("Usuário não encontrado", exception.getMessage());
	}

	@DisplayName("Atualização Deve retornar sucesso.")
	@Test
	public void shouldUpdateReturnSuccess() {

		TarefaRecord tarefaRecord = new TarefaRecord("Nome da Tarefa", new BigDecimal("100.00"), "2023-12-31");

		Usuario usuario = new Usuario();
		usuario.setId(IDUSUARIO);

		Tarefa tarefa = new Tarefa();
		tarefa.setId(1L);
		tarefa.setName(tarefaRecord.name());
		tarefa.setCost(tarefaRecord.cost());
		tarefa.setDueDate(LocalDate.parse(tarefaRecord.dueDate()));
		tarefa.setPosition(1);
		tarefa.setUsuario(usuario);

		when(repository.findById(ID)).thenReturn(Optional.of(tarefa));
		when(repository.save(any(Tarefa.class))).thenReturn(tarefa);

		service.update(tarefaRecord, ID);

		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Tarefa.class));
	}

	@DisplayName("Atualização Deve retornar tarefa não encontrada.")
	@Test
	public void shouldUpdateReturnTarefaNotFound() {
		TarefaRecord tarefaRecord = new TarefaRecord("Nome da Tarefa", new BigDecimal("100.00"), "2023-12-31");
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.update(tarefaRecord, ID));
	}

	@DisplayName("Deve deletar uma tarefa com sucesso.")
	@Test
	public void shouldDeleteTarefaWithSuccess() {
		Tarefa tarefa = tarefaProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(tarefa));
		service.delete(ID);
	}

	@DisplayName("Deve não encontrar uma tarefa ao deletar.")
	@Test
	public void shouldReturnTarefaNotFoundWhenDelete() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.delete(ID));
	}

	@DisplayName("Deve retornar nome da tarefa já existe.")
	@Test
	public void shouldReturnDataIntegratyViolationExceptionWhenNameTarefaExist() {

		String taskName = "Nome da Tarefa";
		when(repository.existsByNameAndUsuarioId(taskName, IDUSUARIO)).thenReturn(true);

		DataIntegratyViolationException exception = assertThrows(DataIntegratyViolationException.class, () -> {
			service.nameAlreadyExists(taskName, IDUSUARIO);
		});

		assertEquals("O usuário já possui uma tarefa com este nome.", exception.getMessage());
		
		verify(repository, times(1)).existsByNameAndUsuarioId(taskName, IDUSUARIO);
	}

	@DisplayName("Não deve lançar exceção quando o nome da tarefa não existe")
	@Test
	public void shouldNotThrowExceptionWhenNameNotExists() {
		String taskName = "Nome da Tarefa";
	    when(repository.existsByNameAndUsuarioId(taskName, IDUSUARIO)).thenReturn(false);
	    
	    assertDoesNotThrow(() -> service.nameAlreadyExists(taskName, IDUSUARIO));
	    
	    verify(repository, times(1)).existsByNameAndUsuarioId(taskName, IDUSUARIO);
	}
	
	@DisplayName("Deve mover a tarefa na lista com sucesso")
	@Test
	public void shouldMoveTeskWithSuccess() {
	    Usuario usuario = new Usuario();
	    usuario.setId(IDUSUARIO);

	    List<Tarefa> tarefas = new ArrayList<>();

	    Tarefa tarefa1 = new Tarefa();
	    tarefa1.setId(1L);
	    tarefa1.setName("Tarefa Teste");
	    tarefa1.setPosition(0);

	    Tarefa tarefa2 = new Tarefa();
	    tarefa2.setId(2L);
	    tarefa2.setName("Tarefa Teste");
	    tarefa2.setPosition(1);

	    tarefas.add(tarefa1);
	    tarefas.add(tarefa2);

	    when(usuarioRepository.findById(IDUSUARIO)).thenReturn(Optional.of(usuario));
	    when(repository.findAllByUsuarioIdOrderByPosition(IDUSUARIO)).thenReturn(tarefas);

	    int sourceIndex = 0;
	    int destinationIndex = 1; 

	    service.moveTarefa(IDUSUARIO, sourceIndex, destinationIndex);

	    verify(repository).findAllByUsuarioIdOrderByPosition(IDUSUARIO);
	    verify(repository).updateBelongingPosition(2L, 0, IDUSUARIO); 
	    verify(repository).updateBelongingPosition(1L, 1, IDUSUARIO); 
	}
	
	@DisplayName("Deve aplicar com sucesso o patch de atualização em uma tarefa existente.")
	@Test
	public void shouldApplyPatchToUpdateTarefa() {
		Tarefa tarefa = tarefaProvider.criar();
		
		Usuario usuario = new Usuario();
	    usuario.setId(IDUSUARIO);
	    
	    tarefa.setUsuario(usuario);
		
		Map<String, Object> fields = new HashMap<>();
		fields.put("name", "teste 1");
		
		when(repository.findById(ID)).thenReturn(Optional.of(tarefa));
		when(repository.save(any(Tarefa.class))).thenReturn(tarefa);
		
		service.patchUpdate(fields, ID);
		
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Tarefa.class));
	}

	@DisplayName("Deve retornar tarefa não encontrado ao tentar aplicar patch de atualização.")
	@Test
	public void shouldReturnTarefaNotFoundWhenApplyingPatchToUpdate() {
		Map<String, Object> fields = new HashMap<>();
		fields.put("name", "teste 1");
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.patchUpdate(fields, ID));
	}

}
