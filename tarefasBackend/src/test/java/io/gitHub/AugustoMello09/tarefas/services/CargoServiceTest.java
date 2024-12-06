package io.gitHub.AugustoMello09.tarefas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.CargoDTO;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Cargo;
import io.gitHub.AugustoMello09.tarefas.provider.CargoDTOProvider;
import io.gitHub.AugustoMello09.tarefas.provider.CargoProvider;
import io.gitHub.AugustoMello09.tarefas.repositories.CargoRepository;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CargoServiceTest {
	
	private static final long ID = 1L;

	@Mock
	private CargoRepository repository;
	
	@InjectMocks
	private CargoService service;
	
	private CargoProvider cargoProvider;
	private CargoDTOProvider cargoDTOProvider;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new CargoService(repository);
		cargoProvider = new CargoProvider();
		cargoDTOProvider = new CargoDTOProvider();
	}
	
	@DisplayName("Deve retornar um Cargo com sucesso.")
	@Test
	public void shouldReturnACargoWithSuccess() {
		Cargo cargoEntity = cargoProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(cargoEntity));

		var response = service.findById(ID);
		assertNotNull(response);
		assertEquals(CargoDTO.class, response.getClass());
		assertEquals(ID, response.getId());
	}

	@DisplayName("Deve retornar Cargo não encontrado.")
	@Test
	public void shouldReturnRoleNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
	}
	
	@DisplayName("Deve criar um cargo com sucesso.")
	@Test
	public void whenCreateThenReturnCargoDTO() {
	    Cargo cargoEntity = cargoProvider.criar();
	    CargoDTO cargoDTOExpected = cargoDTOProvider.criar();

	    when(repository.save(any(Cargo.class))).thenReturn(cargoEntity);

	    CargoDTO result = service.create(cargoDTOExpected);

	    assertNotNull(result);
	    verify(repository, times(1)).save(any(Cargo.class));
	}

}
