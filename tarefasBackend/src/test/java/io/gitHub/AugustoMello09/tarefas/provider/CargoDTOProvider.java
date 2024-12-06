package io.gitHub.AugustoMello09.tarefas.provider;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.CargoDTO;

public class CargoDTOProvider {
	
	private static final long ID = 1L;
	private static final String AUTHORITY = "ROLE_DELE";

	public CargoDTO criar() {
		CargoDTO cargo = new CargoDTO();
		cargo.setId(ID);
		cargo.setAuthority(AUTHORITY);
		return cargo;
	}
}
