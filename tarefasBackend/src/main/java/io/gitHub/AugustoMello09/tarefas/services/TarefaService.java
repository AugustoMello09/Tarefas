package io.gitHub.AugustoMello09.tarefas.services;

import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.tarefas.repositories.TarefaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarefaService {

	private final TarefaRepository repository;
	

}
