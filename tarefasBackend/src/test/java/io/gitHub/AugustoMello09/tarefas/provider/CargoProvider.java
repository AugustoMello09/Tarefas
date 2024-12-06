package io.gitHub.AugustoMello09.tarefas.provider;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Cargo;

public class CargoProvider {
	
	private static final long ID = 1L;
	private static final String AUTHORITY = "ROLE_DELE";

	public Cargo criar() {
		Cargo cargo = new Cargo();
		cargo.setId(ID);
		cargo.setAuthority(AUTHORITY);
		return cargo;
	}
}
