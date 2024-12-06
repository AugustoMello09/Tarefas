package io.gitHub.AugustoMello09.tarefas.domain.dtos;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO {

	private Long id;

	private String authority;
	
	public CargoDTO(Cargo entity) {
		id = entity.getId();
		authority = entity.getAuthority();
	}
}
