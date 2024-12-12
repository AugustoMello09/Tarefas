package io.gitHub.AugustoMello09.tarefas.domain.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Tarefa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private BigDecimal cost;

	private LocalDate dueDate;
	
	private int position;
	
	private Boolean favorite;
	
	public TarefaDTO(Tarefa entity) {
		id = entity.getId();
		name = entity.getName();
		cost = entity.getCost();
		dueDate = entity.getDueDate();
		position = entity.getPosition();
		favorite = entity.getFavorite();
	}

}
