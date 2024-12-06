package io.gitHub.AugustoMello09.tarefas.services.exceptions;

public class DataIntegratyViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DataIntegratyViolationException(String message) {
		super(message);
	}

}
