package io.gitHub.AugustoMello09.tarefas.provider;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.UsuarioDTOInsert;

public class UsuarioDTOInsertProvider {

	private static final String SENHA = "123";

	private static final String NOME = "José";

	private static final String EMAIL = "meuEmail@gmail.com";
	
	public UsuarioDTOInsert criar() {
		UsuarioDTOInsert usuarioDTOInsert = new UsuarioDTOInsert();
		usuarioDTOInsert.setName(NOME);
		usuarioDTOInsert.setEmail(EMAIL);
		usuarioDTOInsert.setSenha(SENHA);
		return usuarioDTOInsert;
	}
}
