package io.gitHub.AugustoMello09.tarefas.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.gitHub.AugustoMello09.tarefas.domain.entities.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
	
	@Query("SELECT COALESCE(MAX(t.position), -1) FROM Tarefa t WHERE t.usuario.id = :usuarioId")
	Integer findMaxPositionByUsuario(@Param("usuarioId") UUID usuarioId);
	
	List<Tarefa> findAllByUsuarioIdOrderByPosition(UUID usuarioId);
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE tb_tarefa t SET t.position = :newPosition WHERE t.id = :id AND t.usuario_id = :usuarioId")
	void updateBelongingPosition(Long id, Integer newPosition, UUID usuarioId);
	
	boolean existsByNameAndUsuarioId(String name, UUID usuarioId);

}
