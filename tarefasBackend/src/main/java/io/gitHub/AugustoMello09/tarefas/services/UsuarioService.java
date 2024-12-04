package io.gitHub.AugustoMello09.tarefas.services;

import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.tarefas.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
}
