package io.gitHub.AugustoMello09.tarefas.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.MoveTarefaRecord;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.TarefaDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.TarefaRecord;
import io.gitHub.AugustoMello09.tarefas.provider.TarefaDTOProvider;
import io.gitHub.AugustoMello09.tarefas.services.TarefaService;

@ExtendWith(MockitoExtension.class)
public class TarefaControllerTest {
	
	private static final long ID = 1L;
	private static final UUID IDUSUARIO = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	@InjectMocks
	private TarefaController controller;

	@Mock
	private TarefaService service;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	private TarefaDTOProvider tarefaDTOProvider;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		tarefaDTOProvider = new TarefaDTOProvider();
		controller = new TarefaController(service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		objectMapper = new ObjectMapper();
	}
	
	@DisplayName("Deve retornar uma tarefa. ")
	@Test
	public void shouldControllerReturnFindByIdWithSuccess() {
		TarefaDTO tarefaDTO = tarefaDTOProvider.criar();
		when(service.findById(ID)).thenReturn(tarefaDTO);
		var response = controller.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(TarefaDTO.class, response.getBody().getClass());
	}
	
	@DisplayName("Deve retornar uma Lista de tarefas. ")
	@Test
	public void shouldReturnListTarefaDTO() {
		List<TarefaDTO> tarefas = new ArrayList<>();
		when(service.listAll(IDUSUARIO)).thenReturn(tarefas);
		ResponseEntity<List<TarefaDTO>> response = controller.listAll(IDUSUARIO);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service).listAll(IDUSUARIO);
	}
	
	@DisplayName("Deve criar uma tarefa. ")
	@Test
	public void shouldReturnCreatedTarefaDTOOnController() throws Exception {
		TarefaRecord tarefaRecord = new TarefaRecord("Nome da Tarefa", new BigDecimal("100.00"), "2023-12-31");
		
		TarefaDTO tarefaDTO = tarefaDTOProvider.criar();
		
		when(service.create(any(TarefaRecord.class), eq(IDUSUARIO))).thenReturn(tarefaDTO);
		
		mockMvc.perform(post("/v1/tarefas/{id}", IDUSUARIO) 
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(tarefaRecord)))
	            .andExpect(status().isCreated());
	}
	
	@DisplayName("Deve atualizar a tarefa. ")
	@Test
	public void shouldReturnUpdateTarefa() {
		TarefaRecord tarefaRecord = new TarefaRecord("Nome da Tarefa", new BigDecimal("100.00"), "2023-12-31");
		doNothing().when(service).update(tarefaRecord, ID);
		ResponseEntity<Void> response = controller.update(tarefaRecord, ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service, times(1)).update(tarefaRecord, ID);
	}
	
	@DisplayName("Deve deletar uma tarefa. ")
	@Test
	public void shouldDeleteTarefaWithSuccess() {
		doNothing().when(service).delete(ID);
		ResponseEntity<Void> response = controller.delete(ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(service, times(1)).delete(ID);
	}
	
	@DisplayName("Deve mover a tarefa na lista. ")
	@Test
	public void shouldMoveTarefaWithSuccess() throws Exception {		
		MoveTarefaRecord move = new MoveTarefaRecord(0, 1);
		doNothing().when(service).moveTarefa(IDUSUARIO, 0, 1);
		ResponseEntity<Void> response = controller.moveTarefa(move, IDUSUARIO);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode()); 
		verify(service).moveTarefa(IDUSUARIO, 0, 1);
	}
	
	@DisplayName("Deve conseguir atualizar parcialmete um usuário. ")
	@Test
	public void shouldPatchUpdateUser() {
		Map<String, Object> fields = new HashMap<>();
	    fields.put("name", "teste 1");	
	    TarefaDTO tarefaDTO = new TarefaDTO();
	    when(service.patchUpdate(fields, ID)).thenReturn(tarefaDTO);
	    ResponseEntity<TarefaDTO> response = controller.patchUpdate(fields, ID);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
	}

}
