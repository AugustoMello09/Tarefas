package io.gitHub.AugustoMello09.tarefas.domain.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank(message = "Campo obrigatório.") String email, @NotBlank(message = "Campo obrigatório.") String senha) {

}
