package io.gitHub.AugustoMello09.tarefas.services.exceptions;

public class AuthorizationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public AuthorizationException(String message) {
		super(message);
	}

}
