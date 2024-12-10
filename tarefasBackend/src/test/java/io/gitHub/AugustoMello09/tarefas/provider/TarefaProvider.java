package io.gitHub.AugustoMello09.tarefas.provider;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Tarefa;

public class TarefaProvider {
	
	private static final int POSITION = 0;
	private static final LocalDate DUEDATE = LocalDate.now().plusDays(2);
	private static final BigDecimal CUSTO = BigDecimal.ZERO;
	private static final String NOME = "Estudar java";
	private static final long ID = 1L;

	public Tarefa criar() {
		Tarefa tarefa = new Tarefa();
		tarefa.setId(ID);
		tarefa.setName(NOME);
		tarefa.setCost(CUSTO);
		tarefa.setDueDate(DUEDATE);
		tarefa.setPosition(POSITION);
		return tarefa;
	}

}
