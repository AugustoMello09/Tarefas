package io.gitHub.AugustoMello09.tarefas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
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

import io.gitHub.AugustoMello09.tarefas.domain.dtos.CargoDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.UsuarioDTOInsert;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Cargo;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Usuario;
import io.gitHub.AugustoMello09.tarefas.provider.UsuarioDTOInsertProvider;
import io.gitHub.AugustoMello09.tarefas.provider.UsuarioDTOProvider;
import io.gitHub.AugustoMello09.tarefas.provider.UsuarioProvider;
import io.gitHub.AugustoMello09.tarefas.repositories.CargoRepository;
import io.gitHub.AugustoMello09.tarefas.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.DataIntegratyViolationException;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	private static final long IDCARGO = 1L;
	
	@Mock
	private UsuarioRepository repository;

	@InjectMocks
	private UsuarioService service;
	
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@Mock
	private CargoRepository cargoRepository;
	
	private UsuarioProvider usuarioProvider;
	private UsuarioDTOProvider usuarioDTOProvider;
	private UsuarioDTOInsertProvider usuarioDTOInsertProvider;
	
	@BeforeEach
	public void setUp() {	
		MockitoAnnotations.openMocks(this);
		usuarioProvider = new UsuarioProvider(passwordEncoder);
		usuarioDTOProvider = new UsuarioDTOProvider();
		usuarioDTOInsertProvider = new UsuarioDTOInsertProvider();
		service = new UsuarioService(repository, cargoRepository, passwordEncoder);
	}
	
	@DisplayName("Deve retornar um Usuario com sucesso.")
	@Test
	public void shouldReturnAUserWithSuccess() {
		Usuario usuario = usuarioProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(usuario));

		var response = service.findById(ID);
		assertNotNull(response);
		assertEquals(UsuarioDTO.class, response.getClass());
		assertEquals(ID, response.getId());
	}
	
	@DisplayName("Deve retornar Usuario não encontrado.")
	@Test
	public void shouldReturnUserNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
	}
	
	@DisplayName("Deve criar um usuário com sucesso.")
	@Test
	public void whenCreateThenReturnUsuarioDTO() {
        UsuarioDTOInsert usuarioDTOInsert = usuarioDTOInsertProvider.criar();
		Usuario usuarioEntity = usuarioProvider.criar();
		

		when(repository.save(any(Usuario.class))).thenReturn(usuarioEntity);
		
		
		service.create(usuarioDTOInsert);

		verify(repository, times(1)).save(any(Usuario.class));
	}
	
	@DisplayName("Deve retornar Email já existe.")
	@Test
	public void shouldReturnDataIntegratyViolationExceptionWhenEmailExist() {
		UsuarioDTO usuarioDTOExpected = usuarioDTOProvider.criar();
	    UUID differentUserId = UUID.fromString("248cf4fc-b379-4e25-8bf4-f73feb06befa"); 
	    Usuario usuarioEntity = new Usuario(differentUserId, "Carlos", "meuEmail@gmail.com", "123");

	    when(repository.findByEmail(usuarioDTOExpected.getEmail()))
	      .thenReturn(Optional.of(usuarioEntity));

	    DataIntegratyViolationException exception = assertThrows(DataIntegratyViolationException.class, () -> {
	        service.emailAlreadyExists(usuarioDTOExpected);
	    });

	    assertEquals("Email já existe", exception.getMessage());
	}
	
	@DisplayName("Não deve lançar exceção quando o e-mail não existe")
	@Test
	public void shouldNotThrowExceptionWhenEmailNotExists() {
		Usuario usuario = usuarioProvider.criar();
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		
		when(repository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.of(usuario));
		
		service.emailAlreadyExists(usuarioDTO);
	}
	
	@DisplayName("Atualização Deve retornar usuário não encontrado.")
	@Test
	public void shouldUpdateReturnUserNotFound() {
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		
		when(repository.findById(ID)).thenReturn(Optional.empty());
		
		assertThrows(ObjectNotFoundException.class, () -> service.updateUser(usuarioDTO, ID));
	}
	
	@DisplayName("Atualização Deve retornar sucesso.")
	@Test
	public void shouldUpdateReturnSuccess() {
		
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		Usuario usuario = usuarioProvider.criar();
		
		when(repository.findById(ID)).thenReturn(Optional.of(usuario));
		when(repository.save(any(Usuario.class))).thenReturn(usuario);
		
		service.updateUser(usuarioDTO, ID);
		
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Usuario.class));
	}
	
	@DisplayName("Deve adicionar cargos ao usuário quando encontrados")
	@Test
	public void shouldAddRolesWhenFound() {
		Usuario usuario = new Usuario();
	    usuario.setCargos(new HashSet<>());

	    UsuarioDTO objDto = new UsuarioDTO();
	    CargoDTO cargoDTO = new CargoDTO();
	    cargoDTO.setId(1L); 
	    objDto.setCargos(List.of(cargoDTO));

	    Cargo cargo = new Cargo();
	    cargo.setId(1L);

	    when(cargoRepository.findById(IDCARGO))
	           .thenReturn(Optional.of(cargo));

	  
	    service.assignRole(usuario, objDto);


	    assertEquals(1, usuario.getCargos().size());
	    assertTrue(usuario.getCargos().contains(cargo));
	}
	
	@DisplayName("Deve lançar exceção Cargo não encontrado")
	@Test
	public void shouldThrowExceptionWhenRoleNotFound() {
	    Usuario usuario = new Usuario();
	    UsuarioDTO objDto = new UsuarioDTO();
	    CargoDTO cargoDTO = new CargoDTO();
	    cargoDTO.setId(1L); 
	    objDto.setCargos(List.of(cargoDTO));

	    when(cargoRepository.findById(1L))
	           .thenReturn(Optional.empty());

	    ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
	        service.assignRole(usuario, objDto);
	    });

	    assertEquals("Cargo não encontrado", exception.getMessage());
	}
	

}
