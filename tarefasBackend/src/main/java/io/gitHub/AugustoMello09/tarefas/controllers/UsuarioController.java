package io.gitHub.AugustoMello09.tarefas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.gitHub.AugustoMello09.tarefas.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
}
