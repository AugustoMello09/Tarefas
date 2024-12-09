package io.gitHub.AugustoMello09.tarefas.domain.dtos;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String accessToken, String refreshToken) {

}
