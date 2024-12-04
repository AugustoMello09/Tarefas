package io.gitHub.AugustoMello09.tarefas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.gitHub.AugustoMello09.tarefas.services.TarefaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/tarefas")
@RequiredArgsConstructor
public class TarefaController {
	
	private final TarefaService service;

}
