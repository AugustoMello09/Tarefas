package io.gitHub.AugustoMello09.tarefas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

}
