package io.gitHub.AugustoMello09.tarefas.domain.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TarefaRecord(@NotBlank(message = "Campo obrigatório") @Size(max = 91, message = "máximo de caracteres é 91") String name, @NotNull(message = "Campo obrigatório") BigDecimal cost, @NotBlank(message = "Campo obrigatório") String dueDate) {

}
