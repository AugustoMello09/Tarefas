package io.gitHub.AugustoMello09.tarefas.repositories;

import java.time.LocalDate;
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
	
	@Query("SELECT t FROM Tarefa t WHERE t.usuario.id = :usuarioId AND t.favorite = true ORDER BY t.position")
    List<Tarefa> findFavoritesByUsuarioIdOrderByPosition(@Param("usuarioId") UUID usuarioId);
	
	@Query("SELECT t FROM Tarefa t WHERE t.usuario.id = :usuarioId AND t.dueDate = CURRENT_DATE ORDER BY t.position")
    List<Tarefa> findTodayTasksByUsuarioIdOrderByPosition(@Param("usuarioId") UUID usuarioId);
	
	@Query("SELECT t FROM Tarefa t WHERE t.usuario.id = :usuarioId AND t.dueDate BETWEEN :startOfWeek AND :endOfWeek ORDER BY t.position")
    List<Tarefa> findWeeklyTasksByUsuarioIdOrderByPosition(
        @Param("usuarioId") UUID usuarioId,
        @Param("startOfWeek") LocalDate startOfWeek,
        @Param("endOfWeek") LocalDate endOfWeek
    );
	
	@Query("SELECT t FROM Tarefa t WHERE t.usuario.id = :usuarioId AND YEAR(t.dueDate) = :year AND MONTH(t.dueDate) = :month ORDER BY t.position")
    List<Tarefa> findMonthlyTasksByUsuarioIdOrderByPosition(
        @Param("usuarioId") UUID usuarioId,
        @Param("year") int year,
        @Param("month") int month
    );

}
