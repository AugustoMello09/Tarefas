package io.gitHub.AugustoMello09.tarefas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
	
	@Query("SELECT COALESCE(MAX(t.position), -1) FROM Tarefa t")
	Integer findMaxPosition();

	Optional<Tarefa> findByName(String name);

	List<Tarefa> findAllByOrderByPosition();

}
