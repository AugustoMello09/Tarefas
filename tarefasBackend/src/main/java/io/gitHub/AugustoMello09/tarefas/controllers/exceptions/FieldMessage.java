package io.gitHub.AugustoMello09.tarefas.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage {
	
	private String fieldName;
	private String message;

}
