package io.gitHub.AugustoMello09.tarefas.domain.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID id;
	
	@Size(max = 124, message = "máximo de caracteres 124")
	@NotBlank(message = "campo obrigatório ")
	private String name;
	
	@Email(message = "Entre com um Email válido.", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")
	@NotBlank(message = "campo obrigatório ")
	private String email;
	
	private List<CargoDTO> cargos = new ArrayList<>();
	
	private List<TarefaDTO> tarefas = new ArrayList<>();
	
	private Boolean notification;
	
	private String imgUrl;
	
	public UsuarioDTO(Usuario entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		entity.getCargos().stream()
		.forEach(car -> this.cargos.add(new CargoDTO(car)));
		entity.getTarefas().stream()
		.forEach(tar -> this.tarefas.add(new TarefaDTO(tar)));
		notification = entity.getNotification();
		imgUrl = entity.getImgUrl();
	}

}
