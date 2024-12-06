package io.gitHub.AugustoMello09.tarefas.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.CargoDTO;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Cargo;
import io.gitHub.AugustoMello09.tarefas.repositories.CargoRepository;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CargoService {
	
	private final CargoRepository repository;
	
	@Transactional(readOnly = true)
	public CargoDTO findById(Long id) {
		Optional<Cargo> obj = repository.findById(id);
		Cargo entity = obj.orElseThrow(() -> new ObjectNotFoundException("cargo não encontrado"));
		return new CargoDTO(entity);
	}
	
	@Transactional
	public CargoDTO create(CargoDTO cargoDTO) {
		Cargo cargo = new Cargo();
		cargo.setAuthority(cargoDTO.getAuthority());
		repository.save(cargo);
		return new CargoDTO(cargo);
	}

}
